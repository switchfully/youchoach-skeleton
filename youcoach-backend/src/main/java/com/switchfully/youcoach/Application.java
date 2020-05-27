package com.switchfully.youcoach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        SpringApplication.run(Application.class, args);
    }
}
