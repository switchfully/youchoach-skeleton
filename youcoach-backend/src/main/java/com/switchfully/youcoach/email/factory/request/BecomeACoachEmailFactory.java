package com.switchfully.youcoach.email.factory.request;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.domain.request.event.BecomeACoachRequestReceived;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.switchfully.youcoach.email.Email.email;

@Component
public class BecomeACoachEmailFactory implements EmailFactory<BecomeACoachRequestReceived> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public BecomeACoachEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<BecomeACoachRequestReceived> getEventType() {
        return BecomeACoachRequestReceived.class;
    }

    @Override
    public Email create(BecomeACoachRequestReceived event) {
        final Context ctx = new Context();
        ctx.setVariable("userName", event.getFullName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coachee/" + event.getId() + "/edit-profile");
        ctx.setVariable("request", event.getRequest().replace("\n", System.lineSeparator()));

        return email()
                .to(environment.getProperty("app.becomeacoach.receiver"))
                .subject(event.getFullName() + " wants to become a Coach")
                .body(this.templateEngine.process("request/BecomeACoach.html", ctx));
    }

}
