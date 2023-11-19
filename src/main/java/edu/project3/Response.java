package edu.project3;

public enum Response {
    OK(200),
    PARTIAL_CONTENT(206),
    NOT_MODIFIED(304),
    FORBIDDEN(403),
    NOT_FOUND(404),
    REQUEST_RANGE_NOT_SATISFIABLE(416),
    UNKNOWN(0);

    private final int code;
    Response(int number) {
        code = number;
    }

    public int getCode() {
        return code;
    }
}
