package edu.project1;

import java.util.NoSuchElementException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleInteractionWithGame implements InteractionWithGame {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public char getSymbol() throws RestartGameException, ExitGameException {
        String inputString;
        char inputChar;
        while (true) {
            try {
                inputString = SCANNER.nextLine();
            } catch (NoSuchElementException ex) {
                throw new ExitGameException();
            }
            if (inputString.length() > 1) {
                LOGGER.error("Enter only one character. Try again.");
                continue;
            } else if (inputString.length() == 0) {
                LOGGER.error("Enter one character. Try again.");
                continue;
            }
            inputChar = inputString.charAt(0);
            inputChar = Character.toLowerCase(inputChar);

            if (inputChar == '!') {
                throw new RestartGameException();
            }
            if (!Character.isLetter(inputChar) || !(inputChar >= 'a' && inputChar <= 'z')) {
                LOGGER.error("Only the English alphabet is allowed. Try again.");
                continue;
            }
            return inputChar;
        }
    }

    @Override
    public void printMessage(String message) {
        LOGGER.info(message);
    }

    @Override
    public void printWarnings(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void printErrors(String message) {
        LOGGER.error(message);
    }
}
