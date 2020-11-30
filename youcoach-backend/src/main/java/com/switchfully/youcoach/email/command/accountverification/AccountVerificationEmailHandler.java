package com.switchfully.youcoach.email.command.accountverification;

import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.email.exception.SendingMailError;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class AccountVerificationEmailHandler implements EmailHandler<AccountVerificationEmailCommand> {

    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public AccountVerificationEmailHandler(EmailSenderService emailSenderService, TemplateEngine templateEngine, Environment environment) {
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<AccountVerificationEmailCommand> getCommandType() {
        return AccountVerificationEmailCommand.class;
    }

    @Override
    public void handle(AccountVerificationEmailCommand command) throws MessagingException {
        String subject = environment.getProperty("app.emailverification.subject");
        String from = environment.getProperty("app.emailverification.sender");

        final Context ctx = new Context();
        ctx.setVariable("fullName", command.getProfile().getFirstName() + " " + command.getProfile().getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.emailverification.firmName"));
        ctx.setVariable("hostName", environment.getProperty("app.emailverification.hostName"));
        ctx.setVariable("url", environment.getProperty("app.emailverification.hostName") + "/validate-account");
        ctx.setVariable("token", command.getAccountVerification().getVerificationCode());
        final String body = this.templateEngine.process("EmailVerificationTemplate.html", ctx);

        emailSenderService.sendMail(from, command.getProfile().getEmail(), subject, body, true);
    }
}
