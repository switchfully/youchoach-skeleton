package com.switchfully.youcoach.email.handler;

import com.switchfully.youcoach.domain.session.event.SessionCreated;
import com.switchfully.youcoach.email.Email;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class SessionCreatedEmailFactory implements EmailFactory<SessionCreated> {

    private final TemplateEngine templateEngine;

    public SessionCreatedEmailFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionCreated> getCommandType() {
        return SessionCreated.class;
    }

    @Override
    public Email create(SessionCreated command) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", command.getCoachName());
        ctx.setVariable("coacheeName", command.getCoacheeName());
        ctx.setVariable("sessionDate", command.getSessionDateAndTime());
        ctx.setVariable("sessionLocation", command.getSessionLocation());
        ctx.setVariable("sessionSubject", command.getSessionSubject());
        ctx.setVariable("sessionRemarks", command.getSessionRemarks());
        String body = this.templateEngine.process("SessionCreated.html", ctx);

        return Email.email()
                .to(command.getCoachEmail())
                .subject("Session Created - Sessie aangemaakt - Session Créé")
                .body(body);
    }
}
