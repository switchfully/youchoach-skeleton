package com.switchfully.youcoach.email.command.becomecoach;

import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.email.exception.SendingMailError;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class BecomeACoachHandler implements EmailHandler<BecomeACoachCommand> {

    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public BecomeACoachHandler(EmailSenderService emailSenderService, TemplateEngine templateEngine, Environment environment) {
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<BecomeACoachCommand> getCommandType() {
        return BecomeACoachCommand.class;
    }

    @Override
    public void handle(BecomeACoachCommand command) throws MessagingException {
        String subject = command.getFullName() + " " + environment.getProperty("app.becomeacoach.subject");
        String from = environment.getProperty("app.becomeacoach.sender");
        String to = environment.getProperty("app.becomeacoach.receiver");

        final Context ctx = new Context();
        ctx.setVariable("userName", command.getFullName());
        ctx.setVariable("firmName", environment.getProperty("app.becomeacoach.firmName"));
        ctx.setVariable("url", environment.getProperty("app.becomeacoach.hostName") + "/coachee/" + command.getId() + "/edit-profile");
        ctx.setVariable("request", command.getRequest().replace("\n", System.lineSeparator()));

        final String body = this.templateEngine.process("BecomeACoach.html", ctx);

        emailSenderService.sendMail(from, to, subject, body, true);
    }

}
