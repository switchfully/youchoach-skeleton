package com.switchfully.youcoach.security.authentication.user.exception;

public class SigningFailedException extends RuntimeException{
    public SigningFailedException(String msg){
        super(msg);
    }
}
