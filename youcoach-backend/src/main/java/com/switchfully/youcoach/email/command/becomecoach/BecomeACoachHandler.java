package com.switchfully.youcoach.email.command.becomecoach;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.switchfully.youcoach.email.Email.email;

@Component
public class BecomeACoachHandler implements EmailHandler<BecomeACoachCommand> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public BecomeACoachHandler(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<BecomeACoachCommand> getCommandType() {
        return BecomeACoachCommand.class;
    }

    @Override
    public Email createEmail(BecomeACoachCommand command) {
        final Context ctx = new Context();
        ctx.setVariable("userName", command.getFullName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coachee/" + command.getId() + "/edit-profile");
        ctx.setVariable("request", command.getRequest().replace("\n", System.lineSeparator()));

        return email()
                .to(environment.getProperty("app.becomeacoach.receiver"))
                .subject(command.getFullName() + " wants to become a Coach")
                .body(this.templateEngine.process("BecomeACoach.html", ctx));
    }

}
