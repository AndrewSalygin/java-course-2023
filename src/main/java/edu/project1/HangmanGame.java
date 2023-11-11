package edu.project1;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class HangmanGame {
    final static String THE_WORD_CONSTRUCTION = "The word: ";

    private Board board;
    private InteractionWithGame interactionWithGame;
    private DictionaryImplementation dictionary;

    public HangmanGame(String[] words, InteractionWithGame interactionWithGame, int maxAttempts) {
        dictionary = new DictionaryImplementation(words);
        board = new Board(dictionary.randomWord(), maxAttempts);
        this.interactionWithGame = interactionWithGame;
    }

    public Board getBoard() {
        return board;
    }

    void initGame() {
        board = new Board(dictionary.randomWord(), board.getMaxAttempts());
    }

    boolean run() {
        if (dictionary.isErroneousWords()) {
            interactionWithGame.printWarnings(
                "Some words from the input dictionary were not loaded due to the rules of the game.");
        }

        interactionWithGame.printMessage("To exit the game, hold down 'Ctrl+D'.");
        interactionWithGame.printMessage("To restart the game, write '!'");

        try {
            initGame();
        } catch (NoSuchElementException ex) {
            interactionWithGame.printErrors(ex.getMessage());
            return false;
        }

        char inputChar;
        GameStatus guessResult = GameStatus.NONE;
        char option;
        while (true) {
            interactionWithGame.printMessage(THE_WORD_CONSTRUCTION + new String(board.getUserAnswer()));
            interactionWithGame.printMessage("Tried letters: " + board.getTriedLetters().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")));
            interactionWithGame.printMessage("Guess a letter:");

            try {
                inputChar = interactionWithGame.getSymbol();
                if (board.getTriedLetters().contains(inputChar)) {
                    interactionWithGame.printErrors("This letter has already been tried. Try again.");
                    continue;
                }
                board.getTriedLetters().add(inputChar);
                guessResult = board.guess(inputChar);
                printState(guessResult);
                if (guessResult == GameStatus.DEFEAT || guessResult == GameStatus.WIN) {
                    break;
                }
            } catch (RuntimeException ex) {
                interactionWithGame.printErrors(ex.getMessage());
            } catch (RestartGameException e) {
                break;
            } catch (ExitGameException e) {
                return false;
            }
        }
        if (guessResult != GameStatus.WIN) {
            interactionWithGame.printMessage("The hidden word was: " + board.getAnswer());
        }
        interactionWithGame.printMessage("Do you want to repeat? (y/N)");
        try {
            option = interactionWithGame.getSymbol();
        } catch (RestartGameException | ExitGameException ex) {
            return false;
        }
        if (option == 'y' || option == 'Y') {
            run();
        }
        return true;
    }

    private void printState(GameStatus guess) {
        if (guess == GameStatus.FAILED_GUESS) {
            interactionWithGame.printMessage(
                guess.getMessage() + board.getAttempt() + " out of " + board.getMaxAttempts() + ".");
        } else {
            interactionWithGame.printMessage(guess.getMessage());
        }
        interactionWithGame.printMessage("");
        interactionWithGame.printMessage(THE_WORD_CONSTRUCTION + new String(board.getUserAnswer()));
        interactionWithGame.printMessage("");
    }
}
