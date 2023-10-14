package edu.hw1.utils;

public enum ExceptionMessageTask5 {
    NUMBER_NOT_NEGATIVE("Число не должно быть отрицательным."),
    NUMBER_CONSIST_AT_LEAST_TWO_DIGITS("Число должно состоять как минимум из двух цифр.");

    private final String message;

    ExceptionMessageTask5(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
