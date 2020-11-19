package com.switchfully.youcoach.email.command.changetopics;

import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class ChangeTopicsEmailHandler implements EmailHandler<ChangeTopicsEmailCommand>{
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public ChangeTopicsEmailHandler(EmailSenderService emailSenderService, TemplateEngine templateEngine, Environment environment) {
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<ChangeTopicsEmailCommand> getCommandType() {
        return ChangeTopicsEmailCommand.class;
    }

    @Override
    public void handle(ChangeTopicsEmailCommand command) throws MessagingException {
        String subject = environment.getProperty("app.changetopics.subject");
        String from = environment.getProperty("app.changetopics.sender");
        String to = environment.getProperty("app.changetopics.receiver");

        final Context ctx = new Context();
        ctx.setVariable("userName", command.getFullName());
        ctx.setVariable("firmName", environment.getProperty("app.changetopics.firmName"));
        ctx.setVariable("url", environment.getProperty("app.changetopics.hostName") + "/coach/" + command.getProfileId() + "/edit-topic");
        ctx.setVariable("request", command.getRequest().replace("\n", System.lineSeparator()));

        final String body = this.templateEngine.process("ChangeTopics.html", ctx);

        emailSenderService.sendMail(from, to, subject, body, true);
    }
}
