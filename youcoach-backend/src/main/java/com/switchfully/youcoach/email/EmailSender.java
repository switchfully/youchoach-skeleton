package com.switchfully.youcoach.email;

import com.switchfully.youcoach.email.command.EmailCommand;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.email.exception.SendingMailError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Profile("!test")
public class EmailSender implements MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender javaMailSender;
    private final Environment environment;
    private final Map<Class<?>, EmailHandler> handlerMap;

    public EmailSender(Environment environment, JavaMailSender javaMailSender, List<EmailHandler<? extends EmailCommand>> emailHandlerList) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
        this.handlerMap = emailHandlerList.stream()
                .collect(toMap(EmailHandler::getCommandType, identity()));
        System.setProperty("mail.mime.charset", "utf8");
    }

    @Override
    public void execute(EmailCommand command) {
        EmailHandler<? super EmailCommand> handler = handlerMap.get(command.getClass());

        if (handler == null) {
            throw new RuntimeException(format("No commandhandler found for command type %s", command.getClass().getSimpleName()));
        }

        try {
            Email email = handler.createEmail(command);

            sendMail(environment.getProperty("app.email.sender"), email.getTo(), email.getSubject(), email.getBody());
        } catch (MessagingException exception) {
            throw new SendingMailError(exception);
        }
    }

    public void sendMail(String from, String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(from);

        logger.info("Sending email with subject " + subject + " to " + to);
        javaMailSender.send(message);

    }
}
