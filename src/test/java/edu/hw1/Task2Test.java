package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Одна цифра 0")
    void oneDigitZeroTest() {
        int result = Task2.countNumberOfDigits(0);
        assertThat(result).isEqualTo(1);
    }

    @ParameterizedTest
    @DisplayName("Положительное число")
    @CsvSource({"3021, 4", "4666, 4", "544, 3"})
    void usualNumberTest(int number, int expectedResult) {
        int result = Task2.countNumberOfDigits(number);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Отрицательное число")
    void negativeNumberTest() {
        int result = Task2.countNumberOfDigits(-3021);
        assertThat(result).isEqualTo(4);
    }
}
