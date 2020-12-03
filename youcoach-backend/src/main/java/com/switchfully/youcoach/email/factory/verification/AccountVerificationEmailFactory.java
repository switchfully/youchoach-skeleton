package com.switchfully.youcoach.email.factory.verification;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import com.switchfully.youcoach.security.verification.event.AccountCreated;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class AccountVerificationEmailFactory implements EmailFactory<AccountCreated> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public AccountVerificationEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<AccountCreated> getEventType() {
        return AccountCreated.class;
    }

    @Override
    public Email create(AccountCreated event) {
        final Context ctx = new Context();
        ctx.setVariable("fullName", event.getProfile().getFirstName() + " " + event.getProfile().getLastName());
        ctx.setVariable("hostName", environment.getProperty("app.email.hostName"));
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/validate-account");
        ctx.setVariable("token", event.getAccountVerification().getVerificationCode());

        return Email.email()
                .to(event.getProfile().getEmail())
                .subject("Lidmaatschapsverificatie - Verification de compte - Account verification")
                .body(this.templateEngine.process("verification/EmailVerificationTemplate.html", ctx));
    }
}
