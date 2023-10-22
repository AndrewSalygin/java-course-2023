package edu.project1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConsoleHangman {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Logger LOGGER = LogManager.getLogger();
    private final static int MAX_ATTEMPTS = 5;
    private final static String THE_WORD_CONSTRUCTION = "The word: ";
    private static HashSet<Character> triedLetters;

    private ConsoleHangman() {}

    public static void run(String[] words) {
        triedLetters = new HashSet<>();
        DictionaryImplementation dictionary = new DictionaryImplementation(words);
        if (dictionary.isErroneousWords) {
            LOGGER.warn("Some words from the input dictionary were not loaded due to the rules of the game.");
        }

        LOGGER.info("To exit the game, hold down 'Ctrl+D'.");
        LOGGER.info("To restart the game, write '!'");
        String word;
        try {
            word = dictionary.randomWord();
        } catch (NoSuchElementException ex) {
            LOGGER.error(ex.getMessage());
            return;
        }

        char inputChar;
        String inputString;
        String option;

        int attempt = 0;
        char[] userAnswer = new char[word.length()];
        Arrays.fill(userAnswer, '*');
        LOGGER.info(THE_WORD_CONSTRUCTION + new String(userAnswer));
        while (true) {
            LOGGER.info("Tried letters: " + triedLetters.stream()
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

            Session session = new Session(word, userAnswer, MAX_ATTEMPTS, attempt);
            try {
                GuessResult guessResult;
                if (inputChar == '!') {
                    guessResult = session.giveUp();
                } else {
                    guessResult = tryGuess(session, inputChar);
                }
                triedLetters.add(inputChar);
                attempt = guessResult.attempt();
                userAnswer = guessResult.state();
                printState(guessResult);
                if (guessResult instanceof GuessResult.Defeat || guessResult instanceof GuessResult.Win) {
                    if (guessResult instanceof GuessResult.Defeat) {
                        LOGGER.info("The hidden word was: " + word);
                    }
                    LOGGER.info("You want to repeat? (y/N)");
                    option = SCANNER.nextLine();
                    if (option.equals("y") || option.equals("Y")) {
                        run(words);
                    }
                    break;
                }
            } catch (RuntimeException ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    private static GuessResult tryGuess(Session session, char ch) {
        if (ch == '!') {
            return session.giveUp();
        }
        if (!Character.isLetter(ch) || !(ch >= 'a' && ch <= 'z')) {
            throw new RuntimeException("Only the English alphabet is allowed. Try again.");
        } else if (triedLetters.contains(ch)) {
            throw new RuntimeException("This letter has already been tried. Try again.");
        }
        return session.guess(ch);
    }

    private static void printState(GuessResult guess) {
        LOGGER.info(guess.message());
        LOGGER.info("");
        LOGGER.info(THE_WORD_CONSTRUCTION + new String(guess.state()));
        LOGGER.info("");
    }
}
