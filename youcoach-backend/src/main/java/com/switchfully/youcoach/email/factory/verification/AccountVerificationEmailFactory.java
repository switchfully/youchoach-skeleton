package com.switchfully.youcoach.email.factory.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.exception.SendingMailError;
import com.switchfully.youcoach.email.factory.EmailFactory;
import com.switchfully.youcoach.security.authentication.user.event.AccountCreated;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class AccountVerificationEmailFactory implements EmailFactory<AccountCreated> {

    private final TemplateEngine templateEngine;
    private final Environment environment;
    private final ProfileRepository profileRepository;

    public AccountVerificationEmailFactory(TemplateEngine templateEngine, Environment environment, ProfileRepository profileRepository) {
        this.templateEngine = templateEngine;
        this.environment = environment;
        this.profileRepository = profileRepository;
    }

    @Override
    public Class<AccountCreated> getEventType() {
        return AccountCreated.class;
    }

    @Override
    public Email create(AccountCreated event) {
        Profile profile = profileRepository.findById(event.getProfileId()).orElseThrow(() -> new SendingMailError("Could not find profile"));

        final Context ctx = new Context();
        ctx.setVariable("fullName", profile.getFullName());
        ctx.setVariable("hostName", environment.getProperty("app.email.hostName"));
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/validate-account");
        ctx.setVariable("token", event.getVerificationCode());

        return Email.email()
                .to(event.getAccount().getEmail())
                .subject("Lidmaatschapsverificatie - Verification de compte - Account verification")
                .body(this.templateEngine.process("verification/EmailVerificationTemplate.html", ctx));
    }
}
