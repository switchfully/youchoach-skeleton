package com.switchfully.youcoach.email.command.changetopics;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.switchfully.youcoach.email.Email.email;

@Component
public class ChangeTopicsEmailHandler implements EmailHandler<ChangeTopicsEmailCommand> {
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public ChangeTopicsEmailHandler(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<ChangeTopicsEmailCommand> getCommandType() {
        return ChangeTopicsEmailCommand.class;
    }

    @Override
    public Email createEmail(ChangeTopicsEmailCommand command) {
        final Context ctx = new Context();
        ctx.setVariable("userName", command.getFullName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coach/" + command.getProfileId() + "/edit-topic");
        ctx.setVariable("request", command.getRequest().replace("\n", System.lineSeparator()));

        return email()
                .to(environment.getProperty("app.changetopics.receiver"))
                .subject("Topic Change Requested")
                .body(this.templateEngine.process("ChangeTopics.html", ctx));
    }
}
