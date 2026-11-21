package com.fitnessclub.fitnessclubbackend.exception;

import java.time.LocalDateTime;

// Класс для хранения информации об ошибке
public class ErrorResponse {
    private int status;       // HTTP-код (404, 400 и т.д.)
    private String message;   // Текст ошибки
    private LocalDateTime timestamp; // Когда произошла ошибка

    // Конструктор
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры, чтобы Jackson превратил объект в JSON
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}