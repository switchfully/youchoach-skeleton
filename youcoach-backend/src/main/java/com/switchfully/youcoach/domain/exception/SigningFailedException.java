package com.switchfully.youcoach.domain.exception;

public class SigningFailedException extends RuntimeException{
    public SigningFailedException(String msg){
        super(msg);
    }
}
