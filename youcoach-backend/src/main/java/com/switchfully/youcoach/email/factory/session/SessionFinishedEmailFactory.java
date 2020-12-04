package com.switchfully.youcoach.email.factory.session;

import com.switchfully.youcoach.domain.session.event.SessionFinished;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class SessionFinishedEmailFactory implements EmailFactory<SessionFinished> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public SessionFinishedEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<SessionFinished> getEventType() {
        return SessionFinished.class;
    }

    @Override
    public Email create(SessionFinished event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coachee/" + event.getProfileId() + "/sessions/" + event.getSessionId() + "#provide-feedback");
        String body = this.templateEngine.process("session/SessionFinished.html", ctx);

        return Email.email()
                .to(event.getCoachEmail())
                .subject("Session Finished - Sessie Beeindigd - Session Terminée")
                .body(body);
    }
}
