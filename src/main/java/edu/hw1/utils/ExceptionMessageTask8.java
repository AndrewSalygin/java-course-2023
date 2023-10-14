package edu.hw1.utils;

public enum ExceptionMessageTask8 {
    BOARD_SIZE_MUST_BE_8x8("Размер доски должен быть 8x8."),
    ZERO_OR_ONE_IN_BOARD("В массиве должны быть 1 или 0.");

    private final String message;

    ExceptionMessageTask8(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
