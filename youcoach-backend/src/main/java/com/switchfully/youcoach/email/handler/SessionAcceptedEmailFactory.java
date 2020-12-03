package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.domain.session.event.SessionAccepted;
import com.switchfully.youcoach.domain.session.event.SessionCreated;
import com.switchfully.youcoach.email.Email;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
public class SessionAcceptedEmailFactory implements EmailFactory<SessionAccepted> {

    private final TemplateEngine templateEngine;

    public SessionAcceptedEmailFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionAccepted> getEventType() {
        return SessionAccepted.class;
    }

    @Override
    public Email create(SessionAccepted event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("sessionDate", event.getSessionDateAndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        ctx.setVariable("sessionLocation", event.getSessionLocation());
        String body = this.templateEngine.process("SessionAccepted.html", ctx);

        return Email.email()
                .to(event.getCoacheeEmail())
                .subject("Session Accepted - Sessie Geaccepteerd - Session Acceptée")
                .body(body);
    }
}
