package edu.project1;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.project1.Board.MAX_ATTEMPTS;
import static edu.project1.Board.answer;
import static edu.project1.Board.attempt;

public class ConsoleHangman extends HangmanGame {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Logger LOGGER = LogManager.getLogger();
    ConsolePlayer consolePlayer;

    ConsoleHangman(String[] words, ConsolePlayer player) {
        super(words);
        consolePlayer = player;
    }

    @Override
    public void run() {
        if (dictionary.isErroneousWords) {
            LOGGER.warn("Some words from the input dictionary were not loaded due to the rules of the game.");
        }

        LOGGER.info("To exit the game, hold down 'Ctrl+D'.");
        LOGGER.info("To restart the game, write '!'");
        try {
            initGame();
        } catch (NoSuchElementException ex) {
            LOGGER.error(ex.getMessage());
            return;
        }

        char inputChar;
        String inputString;
        String option;

        char[] userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, '*');
        consolePlayer.setUserAnswer(userAnswer);
        LOGGER.info(THE_WORD_CONSTRUCTION + new String(userAnswer));
        while (true) {
            LOGGER.info("Tried letters: " + consolePlayer.getTriedLetters().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
            LOGGER.info("Guess a letter:");
            try {
                inputString = SCANNER.nextLine();
            } catch (NoSuchElementException ex) {
                break;
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

            try {
                GameStatus guessResult;
                if (inputChar == '!') {
                    guessResult = consolePlayer.giveUp();
                } else {
                    guessResult = tryGuess(inputChar);
                }
                consolePlayer.getTriedLetters().add(inputChar);
                printState(guessResult);
                if (guessResult == GameStatus.DEFEAT || guessResult == GameStatus.WIN) {
                    if (guessResult == GameStatus.DEFEAT) {
                        LOGGER.info("The hidden word was: " + answer);
                    }
                    LOGGER.info("You want to repeat? (y/N)");
                    option = SCANNER.nextLine();
                    if (option.equals("y") || option.equals("Y")) {
                        run();
                    }
                    break;
                }
            } catch (RuntimeException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    @Override
    void initGame() throws NoSuchElementException {
        attempt = 0;
        answer = dictionary.randomWord();
        consolePlayer = new ConsolePlayer(answer.length());
    }


    public GameStatus tryGuess(char ch) {
        if (ch == '!') {
            return consolePlayer.giveUp();
        }
        if (!Character.isLetter(ch) || !(ch >= 'a' && ch <= 'z')) {
            throw new RuntimeException("Only the English alphabet is allowed. Try again.");
        } else if (consolePlayer.getTriedLetters().contains(ch)) {
            throw new RuntimeException("This letter has already been tried. Try again.");
        }
        return consolePlayer.guess(ch);
    }

    private void printState(GameStatus guess) {
        if (guess == GameStatus.FAILED_GUESS) {
            LOGGER.info(guess.getMessage() + attempt + " out of " + MAX_ATTEMPTS + ".");
        } else {
            LOGGER.info(guess.getMessage());
        }
        LOGGER.info("");
        LOGGER.info(THE_WORD_CONSTRUCTION + new String(consolePlayer.getUserAnswer()));
        LOGGER.info("");
    }
}
