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
        Session session = new Session(answer, new char[] {'m', 'e', 'l', '*', 'n'}, 5, 4);
        GuessResult guessResult = session.guess('o');
        assertThat(guessResult).isInstanceOf(GuessResult.Win.class);
        assertThat(guessResult.state()).containsExactly(answer.toCharArray());
        assertThat(guessResult.attempt()).isNotEqualTo(guessResult.maxAttempts());
    }

    @Test
    @DisplayName("Поражение")
    void defeatTest() {
        Session session = new Session("melon", new char[] {'m', 'e', 'l', '*', 'n'}, 5, 4);
        GuessResult guessResult = session.guess('p');
        assertThat(guessResult).isInstanceOf(GuessResult.Defeat.class);
        assertThat(guessResult.state()).containsExactly('m', 'e', 'l', '*', 'n');
        assertThat(guessResult.attempt()).isEqualTo(guessResult.maxAttempts());
    }

    @Test
    @DisplayName("Буква угадана")
    void successfulGuessTest() {
        Session session = new Session("melon", new char[] {'m', 'e', '*', '*', 'n'}, 5, 4);
        GuessResult guessResult = session.guess('l');
        assertThat(guessResult).isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(guessResult.state()).containsExactly('m', 'e', 'l', '*', 'n');
        assertThat(guessResult.attempt()).isNotEqualTo(guessResult.maxAttempts());
    }

    @Test
    @DisplayName("Буква не угадана")
    void failedGuessTest() {
        Session session = new Session("melon", new char[] {'m', 'e', '*', '*', 'n'}, 5, 3);
        GuessResult guessResult = session.guess('q');
        assertThat(guessResult).isInstanceOf(GuessResult.FailedGuess.class);
        assertThat(guessResult.state()).containsExactly('m', 'e', '*', '*', 'n');
        assertThat(guessResult.attempt()).isNotEqualTo(guessResult.maxAttempts());
    }

    @Test
    @DisplayName("Сдаться")
    void giveUpTest() {
        Session session = new Session("melon", new char[] {'m', 'e', '*', '*', 'n'}, 5, 3);
        GuessResult guessResult = session.giveUp();
        assertThat(guessResult).isInstanceOf(GuessResult.Defeat.class);
        assertThat(guessResult.state()).containsExactly('m', 'e', '*', '*', 'n');
        assertThat(guessResult.attempt()).isNotEqualTo(guessResult.maxAttempts());
    }

    @Test
    @DisplayName("Игра не запускается, если загадываемое слово имеет некорректную длину")
    void gameDoesNotStartTest() {
        DictionaryImplementation dictionary = new DictionaryImplementation(new String[]{"", "a"});
        Throwable thrown = catchThrowable(dictionary::randomWord);
        assertThat(thrown).hasMessage("The dictionary is empty.");
    }

    @Test
    @DisplayName("Формирование словаря")
    void dictionaryFormationTest() throws NoSuchFieldException, IllegalAccessException {
        DictionaryImplementation dictionary = new DictionaryImplementation(new String[]{"", "a", "melon", "яблоко",
            "ГрУшА", "WaTeRMeLoN"});
        Field wordsField = dictionary.getClass().getDeclaredField("words");
        wordsField.setAccessible(true);
        List<String> words = (List<String>) wordsField.get(dictionary);
        assertThat(words).isEqualTo(List.of("melon", "watermelon"));
    }
}
