package com.switchfully.youcoach.security.verification.exception;

public class SigningFailedException extends RuntimeException{
    public SigningFailedException(String msg){
        super(msg);
    }
}
