package edu.hw1.utils;

public enum ExceptionMessageTask6 {
    NUMBER_HAS_ALL_SAME_DIGITS("Число имеет все одинаковые цифры."),
    NUMBER_GREATER_1000_N_CONSIST_4_CHAR("Число должно быть больше 1000 и состоять из 4-ех знаков."),
    WRONG_NUMBER("Число не подходит");

    private final String message;

    ExceptionMessageTask6(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
