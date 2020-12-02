package com.switchfully.youcoach.email.command.sessioncreated;

import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.command.EmailHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class SessionCreatedEmailHandler implements EmailHandler<SessionCreatedEvent> {

    private final TemplateEngine templateEngine;

    public SessionCreatedEmailHandler(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionCreatedEvent> getCommandType() {
        return SessionCreatedEvent.class;
    }

    @Override
    public Email createEmail(SessionCreatedEvent command) {
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
