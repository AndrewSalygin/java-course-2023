package edu.hw6.Task6;

public enum ProtocolType {
    TCP("TCP"),
    UPD("UPD");

    private final String message;
    ProtocolType(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
