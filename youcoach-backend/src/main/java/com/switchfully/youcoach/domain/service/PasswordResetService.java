package com.switchfully.youcoach.domain.service;

import com.switchfully.youcoach.datastore.entities.User;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import com.switchfully.youcoach.domain.dtos.request.PasswordChangeRequestDto;
import com.switchfully.youcoach.domain.dtos.response.PasswordChangeResultDto;
import com.switchfully.youcoach.domain.dtos.request.PasswordResetRequestDto;
import com.switchfully.youcoach.domain.service.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final Environment environment;
    private final ValidationService validationService;
    private final TemplateEngine templateEngine;

    @Autowired
    public PasswordResetService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                EmailSenderService emailSenderService, Environment environment,
                                ValidationService validationService, TemplateEngine templateEngine){

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.environment = environment;
        this.validationService = validationService;
        this.templateEngine = templateEngine;
    }

    public void requestPasswordReset(PasswordResetRequestDto request) {
        if(!validationService.isSigningAndVerifyingAvailable()) return;

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        userOpt.ifPresent(user -> {
            try { sendResetEmail(user); } catch(MessagingException ignore){}
        });
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto request){
        if(digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(request)){
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
            if(userOpt.isPresent()){
                User user = userOpt.get();
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                return new PasswordChangeResultDto(true);
            }
        }

        return new PasswordChangeResultDto(false);

    }

    private boolean digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(PasswordChangeRequestDto request) {
        return validationService.isSigningAndVerifyingAvailable() &&
                validationService.verifyBased64SignaturePasses(request.getToken(),request.getEmail()) &&
                validationService.isPasswordValid(request.getPassword());
    }


    public void sendResetEmail(User user) throws MessagingException {
        String subject = environment.getProperty("app.passwordreset.subject");
        String from = environment.getProperty("app.passwordreset.sender");

        final Context ctx = new Context();
        ctx.setVariable("fullName", user.getFirstName() + " " + user.getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.passwordreset.firmName"));
        ctx.setVariable("hostName", environment.getProperty("app.passwordreset.hostName"));
        ctx.setVariable("url", environment.getProperty("app.passwordreset.hostName") + "/password-reset");
        ctx.setVariable("token", validationService.digitallySignAndEncodeBase64(user.getEmail()));
        final String body = this.templateEngine.process("PasswordResetTemplate.html", ctx);

        emailSenderService.sendMail(from, user.getEmail(), subject, body, true);

    }
}
