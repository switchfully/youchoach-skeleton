package com.switchfully.youcoach.email;

public class Email {
    private String to;
    private String subject;
    private String body;

    public static Email email() {
        return new Email();
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Email to(String to) {
        this.to = to;
        return this;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Email body(String body) {
        this.body = body;
        return this;
    }
}
