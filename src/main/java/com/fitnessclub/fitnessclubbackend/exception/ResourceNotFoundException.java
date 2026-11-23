// Исключение "Ресурс не найден"
package com.fitnessclub.fitnessclubbackend.exception;

// Наследуемся от RuntimeException
public class ResourceNotFoundException extends RuntimeException {

    // Конструктор принимает сообщение об ошибке
    public ResourceNotFoundException(String message) {
        super(message);
    }
}