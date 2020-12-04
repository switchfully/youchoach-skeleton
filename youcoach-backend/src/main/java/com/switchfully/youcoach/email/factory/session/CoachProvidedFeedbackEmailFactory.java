package com.switchfully.youcoach.email.factory.session;

import com.switchfully.youcoach.domain.session.event.CoachProvidedFeedback;
import com.switchfully.youcoach.domain.session.event.CoacheeProvidedFeedback;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class CoachProvidedFeedbackEmailFactory implements EmailFactory<CoachProvidedFeedback> {

    private final TemplateEngine templateEngine;
    private final Environment environment;

    public CoachProvidedFeedbackEmailFactory(TemplateEngine templateEngine, Environment environment) {
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public Class<CoachProvidedFeedback> getEventType() {
        return CoachProvidedFeedback.class;
    }

    @Override
    public Email create(CoachProvidedFeedback event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("url", environment.getProperty("app.email.hostName") + "/coachee/" + event.getProfileId() + "/sessions/" + event.getSessionId() + "#feedback");
        String body = this.templateEngine.process("session/CoachProvidedFeedback.html", ctx);

        return Email.email()
                .to(event.getCoachEmail())
                .subject("Feedback on session - Feedback van Sessie - Rapport de la session")
                .body(body);
    }
}
