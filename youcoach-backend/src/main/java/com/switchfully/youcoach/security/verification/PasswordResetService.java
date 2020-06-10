package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.security.verification.api.PasswordChangeRequestDto;
import com.switchfully.youcoach.security.verification.api.PasswordChangeResultDto;
import com.switchfully.youcoach.security.verification.api.PasswordResetRequestDto;
import com.switchfully.youcoach.email.EmailSenderService;
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
    private final ProfileRepository profileRepository;
    private final EmailSenderService emailSenderService;
    private final Environment environment;
    private final VerificationService verificationService;
    private final TemplateEngine templateEngine;

    @Autowired
    public PasswordResetService(PasswordEncoder passwordEncoder, ProfileRepository profileRepository,
                                EmailSenderService emailSenderService, Environment environment,
                                VerificationService verificationService, TemplateEngine templateEngine){

        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.emailSenderService = emailSenderService;
        this.environment = environment;
        this.verificationService = verificationService;
        this.templateEngine = templateEngine;
    }

    public void requestPasswordReset(PasswordResetRequestDto request) {
        if(!verificationService.isSigningAndVerifyingAvailable()) return;

        Optional<Profile> userOpt = profileRepository.findByEmail(request.getEmail());
        userOpt.ifPresent(user -> {
            try { sendResetEmail(user); } catch(MessagingException ignore){}
        });
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto request){
        if(digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(request)){
            Optional<Profile> userOpt = profileRepository.findByEmail(request.getEmail());
            if(userOpt.isPresent()){
                Profile profile = userOpt.get();
                profile.setPassword(passwordEncoder.encode(request.getPassword()));
                return new PasswordChangeResultDto(true);
            }
        }

        return new PasswordChangeResultDto(false);

    }

    private boolean digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(PasswordChangeRequestDto request) {
        return verificationService.isSigningAndVerifyingAvailable() &&
                verificationService.verifyBased64SignaturePasses(request.getToken(),request.getEmail()) &&
                verificationService.isPasswordValid(request.getPassword());
    }


    public void sendResetEmail(Profile profile) throws MessagingException {
        String subject = environment.getProperty("app.passwordreset.subject");
        String from = environment.getProperty("app.passwordreset.sender");

        final Context ctx = new Context();
        ctx.setVariable("fullName", profile.getFirstName() + " " + profile.getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.passwordreset.firmName"));
        ctx.setVariable("hostName", environment.getProperty("app.passwordreset.hostName"));
        ctx.setVariable("url", environment.getProperty("app.passwordreset.hostName") + "/password-reset");
        ctx.setVariable("token", verificationService.digitallySignAndEncodeBase64(profile.getEmail()));
        final String body = this.templateEngine.process("PasswordResetTemplate.html", ctx);

        emailSenderService.sendMail(from, profile.getEmail(), subject, body, true);

    }
}
