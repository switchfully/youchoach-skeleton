package com.switchfully.youcoach.email.factory.session;

import com.switchfully.youcoach.domain.session.event.SessionCreated;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
public class SessionCreatedEmailFactory implements EmailFactory<SessionCreated> {

    private final TemplateEngine templateEngine;

    public SessionCreatedEmailFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionCreated> getEventType() {
        return SessionCreated.class;
    }

    @Override
    public Email create(SessionCreated event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("sessionDate", event.getSessionDateAndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        ctx.setVariable("sessionLocation", event.getSessionLocation());
        ctx.setVariable("sessionSubject", event.getSessionSubject());
        ctx.setVariable("sessionRemarks", event.getSessionRemarks());
        String body = this.templateEngine.process("session/SessionCreated.html", ctx);

        return Email.email()
                .to(event.getCoachEmail())
                .subject("Session Created - Sessie aangemaakt - Session Créé")
                .body(body);
    }
}
