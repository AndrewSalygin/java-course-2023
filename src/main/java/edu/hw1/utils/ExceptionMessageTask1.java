package edu.hw1.utils;

public enum ExceptionMessageTask1 {
    TIME_FORMAT_MMSS("Время должно быть введено в формате mm:ss."),
    FAILED_CONVERT_TIME_FORMAT("Неудачная попытка преобразования времени в численный формат.");

    private final String message;

    ExceptionMessageTask1(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
