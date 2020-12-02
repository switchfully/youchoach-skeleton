package com.switchfully.youcoach.email.command.accountverification;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class AccountVerificationEmailHandler implements EmailHandler<AccountVerificationEmailCommand> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public AccountVerificationEmailHandler(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<AccountVerificationEmailCommand> getCommandType() {
        return AccountVerificationEmailCommand.class;
    }

    @Override
    public Email createEmail(AccountVerificationEmailCommand command) {
        final Context ctx = new Context();
        ctx.setVariable("fullName", command.getProfile().getFirstName() + " " + command.getProfile().getLastName());
        ctx.setVariable("hostName", environment.getProperty("app.email.hostName"));
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/validate-account");
        ctx.setVariable("token", command.getAccountVerification().getVerificationCode());

        return Email.email()
                .to(command.getProfile().getEmail())
                .subject("Lidmaatschapsverificatie - Verification de compte - Account verification")
                .body(this.templateEngine.process("EmailVerificationTemplate.html", ctx));
    }
}
