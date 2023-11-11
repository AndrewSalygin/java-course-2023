package edu.project1;

public interface InteractionWithGame {

    char getSymbol() throws RestartGameException, ExitGameException;

    void printMessage(String message);

    void printWarnings(String message);

    void printErrors(String message);
}
