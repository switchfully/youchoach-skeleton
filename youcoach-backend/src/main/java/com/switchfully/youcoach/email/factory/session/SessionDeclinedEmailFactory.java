package com.switchfully.youcoach.email.factory.session;

import com.switchfully.youcoach.domain.session.event.SessionCancelled;
import com.switchfully.youcoach.domain.session.event.SessionDeclined;
import com.switchfully.youcoach.email.Email;
import com.switchfully.youcoach.email.factory.EmailFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
public class SessionDeclinedEmailFactory implements EmailFactory<SessionDeclined> {

    private final TemplateEngine templateEngine;

    public SessionDeclinedEmailFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Class<SessionDeclined> getEventType() {
        return SessionDeclined.class;
    }

    @Override
    public Email create(SessionDeclined event) {
        final Context ctx = new Context();
        ctx.setVariable("coachName", event.getCoachName());
        ctx.setVariable("coacheeName", event.getCoacheeName());
        ctx.setVariable("sessionDate", event.getSessionDateAndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        ctx.setVariable("sessionLocation", event.getSessionLocation());
        String body = this.templateEngine.process("session/SessionDeclined.html", ctx);

        return Email.email()
                .to(event.getCoacheeEmail())
                .body(body)
                .subject("Session Declined - Sessie Geweigerd - Session Refusée");
    }
}
