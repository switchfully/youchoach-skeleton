package com.switchfully.youcoach.email;

public class Email {
    private String from;
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

    public String getFrom() {
        return from;
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

    public Email from(String from) {
        this.from = from;
        return this;
    }

    @Override
    public String toString() {
        return getSubject() + " to " + getTo();
    }
}
