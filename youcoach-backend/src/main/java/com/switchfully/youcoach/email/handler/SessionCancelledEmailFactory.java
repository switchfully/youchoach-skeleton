package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.domain.session.event.SessionCancelled;
import com.switchfully.youcoach.email.Email;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
public class SessionCancelledEmailFactory implements EmailFactory<SessionCancelled> {

    private final TemplateEngine templateEngine;

    public SessionCancelledEmailFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionCancelled> getEventType() {
        return SessionCancelled.class;
    }

    @Override
    public Email create(SessionCancelled event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("sessionDate", event.getSessionDateAndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        ctx.setVariable("sessionLocation", event.getSessionLocation());
        String body = this.templateEngine.process("SessionCancelled.html", ctx);

        return Email.email()
                .to(event.getCoachEmail())
                .body(body)
                .subject("Session Cancelled - Sessie Geannuleerd - Session Annulée");
    }
}
