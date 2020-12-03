package com.switchfully.youcoach.email;

import com.switchfully.youcoach.domain.Event;
import com.switchfully.youcoach.email.factory.EmailFactory;
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
    private final Map<Class<?>, EmailFactory> factoryMap;

    public EmailSender(Environment environment, JavaMailSender javaMailSender, List<EmailFactory<? extends Event>> emailFactoryList) {
        this.javaMailSender = javaMailSender;
        this.environment = environment;
        this.factoryMap = emailFactoryList.stream()
                .collect(toMap(EmailFactory::getEventType, identity()));
        System.setProperty("mail.mime.charset", "utf8");
    }

    @Override
    public void handle(Event event) {
        EmailFactory<? super Event> factory = factoryMap.get(event.getClass());

        if (factory == null) {
            throw new RuntimeException(format("No commandhandler found for command type %s", event.getClass().getSimpleName()));
        }

        try {
            Email email = factory.create(event)
                    .from(environment.getProperty("app.email.sender"));

            sendMail(email);
        } catch (MessagingException exception) {
            throw new SendingMailError(exception);
        }
    }

    public void sendMail(Email email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody(), true);
        helper.setFrom(email.getFrom());

        logger.info("Sending email " + email);
        javaMailSender.send(message);

    }

}
