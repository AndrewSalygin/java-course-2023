package edu.hw1.utils;

public enum ExceptionMessageTask7 {
    NUMBER_MUST_BE_POSITIVE("Число должно быть только положительным целым.");

    private final String message;

    ExceptionMessageTask7(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
