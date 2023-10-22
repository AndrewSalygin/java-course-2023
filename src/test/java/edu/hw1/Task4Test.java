package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @ParameterizedTest
    @DisplayName("Четное количество символов строки")
    @CsvSource({"123456, 214365", "hTsii  s aimex dpus rtni.g, This is a mixed up string."})
    void evenNumberOfCharsTest(String inputString, String expectedString) {
        String result = Task4.fixStringOrder(inputString);
        assertThat(result).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Нечетное количество символов строки")
    void oddNumberOfCharsTest() {
        String result = Task4.fixStringOrder("badce");
        assertThat(result).isEqualTo("abcde");
    }

    @Test
    @DisplayName("Пустая строка")
    void emptyStringTest() {
        String result = Task4.fixStringOrder("");
        assertThat(result).isEqualTo("");
    }
}
