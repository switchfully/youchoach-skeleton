package com.switchfully.youcoach.email.factory.session;

import com.switchfully.youcoach.domain.session.event.CoacheeProvidedFeedback;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class CoacheeProvidedFeedbackEmailFactory implements EmailFactory<CoacheeProvidedFeedback> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public CoacheeProvidedFeedbackEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<CoacheeProvidedFeedback> getEventType() {
        return CoacheeProvidedFeedback.class;
    }

    @Override
    public Email create(CoacheeProvidedFeedback event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coach/" + event.getProfileId() + "/sessions/" + event.getSessionId());
        String body = this.templateEngine.process("session/CoacheeProvidedFeedback.html", ctx);

        return Email.email()
                .to(event.getCoachEmail())
                .subject("Feedback on session - Feedback van Sessie - Rapport de la session")
                .body(body);
    }
}
