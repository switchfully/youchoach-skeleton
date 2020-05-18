package com.switchfully.youcoach.domain.service;

public class SigningFailedException extends RuntimeException{
    SigningFailedException(String msg){
        super(msg);
    }
}
