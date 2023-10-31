package edu.project1;

public enum GameStatus {
    NONE("None"),
    WIN("You won!"),
    DEFEAT("You lost!"),
    SUCCESS_GUESS("Hit!"),
    FAILED_GUESS("Missed, mistake ");

    final String message;
    GameStatus(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
