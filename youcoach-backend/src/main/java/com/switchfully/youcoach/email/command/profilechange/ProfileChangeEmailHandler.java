package com.switchfully.youcoach.email.command.profilechange;

import com.switchfully.youcoach.email.EmailSenderService;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class ProfileChangeEmailHandler implements EmailHandler<ProfileChangeEmailCommand>{
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public ProfileChangeEmailHandler(EmailSenderService emailSenderService, TemplateEngine templateEngine, Environment environment) {
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<ProfileChangeEmailCommand> getCommandType() {
        return ProfileChangeEmailCommand.class;
    }

    @Override
    public void handle(ProfileChangeEmailCommand command) throws MessagingException {
        String subject = environment.getProperty("app.profilechange.subject");
        String from = environment.getProperty("app.profilechange.sender");
        String to = environment.getProperty("app.profilechange.receiver");

        final Context ctx = new Context();
        ctx.setVariable("userName", command.getProfile().getFirstName() + " " + command.getProfile().getLastName());
        ctx.setVariable("firmName", environment.getProperty("app.profilechange.firmName"));
        ctx.setVariable("url", environment.getProperty("app.profilechange.hostName") + "/coachee/" + command.getProfile().getId() + "/edit-profile");

        final String body = this.templateEngine.process("ProfileChange.html", ctx);

        emailSenderService.sendMail(from, to, subject, body, true);
    }
}
