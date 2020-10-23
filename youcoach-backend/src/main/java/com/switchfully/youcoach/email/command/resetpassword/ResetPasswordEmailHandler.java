package com.switchfully.youcoach.email.command.resetpassword;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.security.verification.VerificationService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Optional;

@Component
public class ResetPasswordEmailHandler implements EmailHandler<ResetPasswordEmailCommand> {

    private final EmailSenderService emailSenderService;
    private final VerificationService verificationService;
    private final ProfileRepository profileRepository;
    private final Environment environment;
    private final TemplateEngine templateEngine;

    public ResetPasswordEmailHandler(EmailSenderService emailSenderService, VerificationService verificationService, ProfileRepository profileRepository, Environment environment, TemplateEngine templateEngine) {
        this.emailSenderService = emailSenderService;
        this.verificationService = verificationService;
        this.profileRepository = profileRepository;
        this.environment = environment;
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<ResetPasswordEmailCommand> getCommandType() {
        return ResetPasswordEmailCommand.class;
    }

    @Override
    public void handle(ResetPasswordEmailCommand command) throws MessagingException {
        Optional<Profile> userOpt = profileRepository.findByEmail(command.getEmail());
        if(userOpt.isPresent()) {
            sendResetEmail(userOpt.get());
        }
    }

    private void sendResetEmail(Profile profile) throws MessagingException {
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
