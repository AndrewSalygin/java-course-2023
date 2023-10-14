package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task5Test {
    @Test
    @DisplayName("Две цифры в числе (true)")
    void twoDigitsInNumberSuccessTest() {
        boolean result = Task5.isSpecialPalindrome(11);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Две цифры в числе (false)")
    void twoDigitsInNumberFailedTest() {
        boolean result = Task5.isSpecialPalindrome(10);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Одна цифра в числе")
    void oneDigitInNumberTest() {
        Throwable thrown = catchThrowable(() -> Task5.isSpecialPalindrome(1));
        assertThat(thrown).hasMessage("Число должно состоять как минимум из двух цифр.");
    }

    @ParameterizedTest
    @DisplayName("Цифр больше 2, чётное количество цифр (true)")
    @CsvSource({"11211230", "13001120", "23336014"})
    void moreTwoDigitsInEvenNumberSuccessTest(int number) {
        boolean result = Task5.isSpecialPalindrome(number);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Цифр больше 2, нечётное количество цифр (true)")
    @CsvSource({"23345", "45339"})
    void moreTwoDigitsInOddNumberSuccessTest(int number) {
        boolean result = Task5.isSpecialPalindrome(number);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Цифр больше 2, чётное количество цифр (false)")
    @CsvSource({"1234"})
    void moreTwoDigitsInEvenNumberFailedTest(int number) {
        boolean result = Task5.isSpecialPalindrome(number);
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @DisplayName("Цифр больше 2, нечётное количество цифр (false)")
    @CsvSource({"45779"})
    void moreTwoDigitsInOddNumberFailedTest(int number) {
        boolean result = Task5.isSpecialPalindrome(number);
        assertThat(result).isFalse();
    }
    @ParameterizedTest
    @DisplayName("Отрицательное число")
    @CsvSource({"-22", "-1"})
    void negativeNumberTest(int number) {
        Throwable thrown = catchThrowable(() -> Task5.isSpecialPalindrome(number));
        assertThat(thrown).hasMessage("Число не должно быть отрицательным.");
    }

    @ParameterizedTest
    @DisplayName("Число уже палиндром")
    @CsvSource({"434", "8558", "75457"})
    void numberIsAlreadyPalindromeTest(int number) {
        boolean result = Task5.isSpecialPalindrome(number);
        assertThat(result).isTrue();
    }
}
