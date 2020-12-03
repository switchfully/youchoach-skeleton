package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.domain.request.event.BecomeACoachRequestReceived;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.switchfully.youcoach.email.Email.email;

@Component
public class BecomeACoachFactory implements EmailFactory<BecomeACoachRequestReceived> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public BecomeACoachFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<BecomeACoachRequestReceived> getCommandType() {
        return BecomeACoachRequestReceived.class;
    }

    @Override
    public Email create(BecomeACoachRequestReceived command) {
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
