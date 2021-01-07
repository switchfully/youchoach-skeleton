package com.switchfully.youcoach.email.factory.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import com.switchfully.youcoach.security.authentication.user.event.ResetPasswordRequestReceived;
import com.switchfully.youcoach.email.exception.SendingMailError;
import com.switchfully.youcoach.security.authentication.user.password.reset.SignatureService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ResetPasswordEmailFactory implements EmailFactory<ResetPasswordRequestReceived> {

    private final ProfileRepository profileRepository;
    private final Environment environment;
    private final TemplateEngine templateEngine;

    public ResetPasswordEmailFactory(ProfileRepository profileRepository, Environment environment, TemplateEngine templateEngine) {
        this.profileRepository = profileRepository;
        this.environment = environment;
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<ResetPasswordRequestReceived> getEventType() {
        return ResetPasswordRequestReceived.class;
    }

    @Override
    public Email create(ResetPasswordRequestReceived event) {
        Profile profile = profileRepository.findByEmail(event.getEmail()).orElseThrow(() -> new SendingMailError("Could not find profile"));

        final Context ctx = new Context();
        ctx.setVariable("fullName", profile.getFirstName() + " " + profile.getLastName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/password-reset");
        ctx.setVariable("token", event.getSignature());
        String body = this.templateEngine.process("verification/PasswordResetTemplate.html", ctx);

        return Email.email()
                .to(profile.getEmail())
                .subject("Paswoord reset - Reset de mot de passe - Password reset")
                .body(body);
    }
}
