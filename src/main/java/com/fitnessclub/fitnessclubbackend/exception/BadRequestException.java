// Исключение "Некорректный запрос"
package com.fitnessclub.fitnessclubbackend.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}