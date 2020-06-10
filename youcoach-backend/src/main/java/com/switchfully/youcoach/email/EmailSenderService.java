package com.switchfully.youcoach.email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        System.setProperty("mail.mime.charset", "utf8");
    }

    public void sendMail(String from, String to, String subject, String body, boolean bodyIsHTML) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, bodyIsHTML);
        helper.setFrom(from);

        logger.info("Sending email with subject " + subject + " to " + to);
        javaMailSender.send(message);

    }
}


