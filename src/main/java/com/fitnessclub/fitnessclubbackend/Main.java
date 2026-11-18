package com.fitnessclub.fitnessclubbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("\n=== Fitness Club Backend запущен! ===");
        System.out.println("Открой браузер и перейди по адресу: http://localhost:8080/api/memberships");
    }
}