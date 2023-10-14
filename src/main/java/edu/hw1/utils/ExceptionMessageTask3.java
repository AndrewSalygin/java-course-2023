package edu.hw1.utils;

public enum ExceptionMessageTask3 {
    BOTH_ARRAYS_NOT_EMPTY("Оба массива не должны быть пустыми.");

    private final String message;

    ExceptionMessageTask3(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
