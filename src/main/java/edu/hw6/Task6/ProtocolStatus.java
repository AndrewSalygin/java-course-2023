package edu.hw6.Task6;

public enum ProtocolStatus {
    FREE("Свободно"),
    OCCUPIED("Занят"),
    UNKNOWN("Неизвестно");

    private final String message;
    ProtocolStatus(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
