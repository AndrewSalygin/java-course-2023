package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Project1Test {
    @Test
    @DisplayName("Победа")
    void winTest() {
        HangmanGame hangmanGame = new HangmanGame(new String[]{"melon"}, new ConsoleInteractionWithGame(), 5);
        Board board = hangmanGame.getBoard();
        board.guess('m');
        board.guess('e');
        board.guess('l');
        board.guess('o');
        GameStatus gameStatusResult = board.guess('n');
        assertThat(gameStatusResult).isEqualTo(GameStatus.WIN);
        assertThat(board.getUserAnswer()).containsExactly(board.getAnswer().toCharArray());
        assertThat(board.getAttempt()).isNotEqualTo(board.getMaxAttempts());
    }

    @Test
    @DisplayName("Поражение")
    void defeatTest() {
        HangmanGame hangmanGame = new HangmanGame(new String[]{"melon"}, new ConsoleInteractionWithGame(), 5);
        Board board = hangmanGame.getBoard();
        board.guess('m');
        board.guess('e');
        board.guess('l');
        board.guess('o');
        board.guess('q');
        board.guess('q');
        board.guess('q');
        board.guess('q');
        GameStatus gameStatusResult = board.guess('q');
        assertThat(gameStatusResult).isEqualTo(GameStatus.DEFEAT);
        assertThat(board.getUserAnswer()).containsExactly('m', 'e', 'l', 'o', '*');
        assertThat(board.getAttempt()).isEqualTo(board.getMaxAttempts());
    }

    @Test
    @DisplayName("Буква угадана")
    void successfulGuessTest() {
        HangmanGame hangmanGame = new HangmanGame(new String[]{"melon"}, new ConsoleInteractionWithGame(), 5);
        Board board = hangmanGame.getBoard();
        GameStatus gameStatusResult = board.guess('m');
        assertThat(gameStatusResult).isEqualTo(GameStatus.SUCCESS_GUESS);
        assertThat(board.getUserAnswer()).containsExactly('m', '*', '*', '*', '*');
        assertThat(board.getAttempt()).isNotEqualTo(board.getMaxAttempts());
    }

    @Test
    @DisplayName("Буква не угадана")
    void failedGuessTest() {
        HangmanGame hangmanGame = new HangmanGame(new String[]{"melon"}, new ConsoleInteractionWithGame(), 5);
        Board board = hangmanGame.getBoard();
        GameStatus gameStatusResult = board.guess('q');
        assertThat(gameStatusResult).isEqualTo(GameStatus.FAILED_GUESS);
        assertThat(board.getUserAnswer()).containsExactly('*', '*', '*', '*', '*');
        assertThat(board.getAttempt()).isNotEqualTo(board.getMaxAttempts());
    }

    @Test
    @DisplayName("Игра не запускается, если загадываемое слово имеет некорректную длину")
    void gameDoesNotStartTest() {
        DictionaryImplementation dictionary = new DictionaryImplementation(new String[] {"", "a"});
        Throwable thrown = catchThrowable(dictionary::randomWord);
        assertThat(thrown).hasMessage("The dictionary is empty.");
    }

    @Test
    @DisplayName("Формирование словаря")
    void dictionaryFormationTest() throws NoSuchFieldException, IllegalAccessException {
        DictionaryImplementation dictionary = new DictionaryImplementation(new String[] {"", "a", "melon", "яблоко",
            "ГрУшА", "WaTeRMeLoN"});
        Field wordsField = dictionary.getClass().getDeclaredField("words");
        wordsField.setAccessible(true);
        List<String> words = (List<String>) wordsField.get(dictionary);
        assertThat(words).isEqualTo(List.of("melon", "watermelon"));
    }
}
