package com.switchfully.youcoach.email.command.resetpassword;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.email.exception.SendingMailError;
import com.switchfully.youcoach.security.verification.VerificationService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ResetPasswordEmailHandler implements EmailHandler<ResetPasswordEmailCommand> {

    private final VerificationService verificationService;
    private final ProfileRepository profileRepository;
    private final Environment environment;
    private final TemplateEngine templateEngine;

    public ResetPasswordEmailHandler(VerificationService verificationService, ProfileRepository profileRepository, Environment environment, TemplateEngine templateEngine) {
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
    public Email createEmail(ResetPasswordEmailCommand command) {
        Profile profile = profileRepository.findByEmail(command.getEmail()).orElseThrow(() -> new SendingMailError("Could not find profile"));

        final Context ctx = new Context();
        ctx.setVariable("fullName", profile.getFirstName() + " " + profile.getLastName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/password-reset");
        ctx.setVariable("token", verificationService.digitallySignAndEncodeBase64(profile.getEmail()));
        String body = this.templateEngine.process("PasswordResetTemplate.html", ctx);

        return Email.email()
                .to(profile.getEmail())
                .subject("Paswoord reset - Reset de mot de passe - Password reset")
                .body(body);
    }
}
