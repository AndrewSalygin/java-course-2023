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
        String answer = "melon";
        ConsolePlayer player = new ConsolePlayer(answer.length());
        ConsoleHangman consoleHangman = new ConsoleHangman(new String[] {"melon"}, player);
        consoleHangman.initGame();
        player.guess('m');
        player.guess('e');
        player.guess('l');
        player.guess('o');
        GameStatus gameStatusResult = player.guess('n');
        assertThat(gameStatusResult).isEqualTo(GameStatus.WIN);
        assertThat(player.getUserAnswer()).containsExactly(answer.toCharArray());
        assertThat(Board.attempt).isNotEqualTo(Board.MAX_ATTEMPTS);
    }

    @Test
    @DisplayName("Поражение")
    void defeatTest() {
        String answer = "melon";
        ConsolePlayer player = new ConsolePlayer(answer.length());
        ConsoleHangman consoleHangman = new ConsoleHangman(new String[] {"melon"}, player);
        consoleHangman.initGame();
        player.guess('m');
        player.guess('e');
        player.guess('l');
        player.guess('o');
        player.guess('q');
        player.guess('q');
        player.guess('q');
        player.guess('q');
        GameStatus gameStatusResult = player.guess('q');
        assertThat(gameStatusResult).isEqualTo(GameStatus.DEFEAT);
        assertThat(player.getUserAnswer()).containsExactly('m', 'e', 'l', 'o', '*');
        assertThat(Board.attempt).isEqualTo(Board.MAX_ATTEMPTS);
    }

    @Test
    @DisplayName("Буква угадана")
    void successfulGuessTest() {
        String answer = "melon";
        ConsolePlayer player = new ConsolePlayer(answer.length());
        ConsoleHangman consoleHangman = new ConsoleHangman(new String[] {"melon"}, player);
        consoleHangman.initGame();
        GameStatus gameStatusResult = player.guess('m');
        assertThat(gameStatusResult).isEqualTo(GameStatus.SUCCESS_GUESS);
        assertThat(player.getUserAnswer()).containsExactly('m', '*', '*', '*', '*');
        assertThat(Board.attempt).isNotEqualTo(Board.MAX_ATTEMPTS);
    }

    @Test
    @DisplayName("Буква не угадана")
    void failedGuessTest() {
        String answer = "melon";
        ConsolePlayer player = new ConsolePlayer(answer.length());
        ConsoleHangman consoleHangman = new ConsoleHangman(new String[] {"melon"}, player);
        consoleHangman.initGame();
        GameStatus gameStatusResult = player.guess('q');
        assertThat(gameStatusResult).isEqualTo(GameStatus.FAILED_GUESS);
        assertThat(player.getUserAnswer()).containsExactly('*', '*', '*', '*', '*');
        assertThat(Board.attempt).isNotEqualTo(Board.MAX_ATTEMPTS);
    }

    @Test
    @DisplayName("Сдаться")
    void giveUpTest() {
        String answer = "melon";
        ConsolePlayer player = new ConsolePlayer(answer.length());
        ConsoleHangman consoleHangman = new ConsoleHangman(new String[] {"melon"}, player);
        consoleHangman.initGame();
        GameStatus gameStatusResult = player.giveUp();
        assertThat(gameStatusResult).isEqualTo(GameStatus.DEFEAT);
        assertThat(player.getUserAnswer()).containsExactly('*', '*', '*', '*', '*');
        assertThat(Board.attempt).isNotEqualTo(Board.MAX_ATTEMPTS);
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
