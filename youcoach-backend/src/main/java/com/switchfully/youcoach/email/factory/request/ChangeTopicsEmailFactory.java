package com.switchfully.youcoach.email.factory.request;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.domain.request.event.ChangeTopicsRequestReceived;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.switchfully.youcoach.email.Email.email;

@Component
public class ChangeTopicsEmailFactory implements EmailFactory<ChangeTopicsRequestReceived> {
    private final TemplateEngine templateEngine;
    private final Environment environment;

    public ChangeTopicsEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<ChangeTopicsRequestReceived> getEventType() {
        return ChangeTopicsRequestReceived.class;
    }

    @Override
    public Email create(ChangeTopicsRequestReceived event) {
        final Context ctx = new Context();
        ctx.setVariable("userName", event.getFullName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coach/" + event.getProfileId() + "/edit-topic");
        ctx.setVariable("request", event.getRequest().replace("\n", System.lineSeparator()));

        return email()
                .to(environment.getProperty("app.changetopics.receiver"))
                .subject("Topic Change Requested")
                .body(this.templateEngine.process("request/ChangeTopics.html", ctx));
    }
}
