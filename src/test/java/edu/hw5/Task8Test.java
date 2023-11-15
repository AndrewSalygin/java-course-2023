package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {
    @Test
    @DisplayName("Строка нечетной длины")
    void oddLengthTest() {
        boolean result = Task8.oddLengthSequence("111");
        assertTrue(result);
    }

    @Test
    @DisplayName("Строка четной длины")
    void oddLengthFailedTest() {
        boolean result = Task8.oddLengthSequence("1110");
        assertFalse(result);
    }

    @Test
    @DisplayName("Неверный ввод (нечетной длины)")
    void wrongFormatOddLengthSequenceTest() {
        boolean result = Task8.oddLengthSequence("11a10");
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Правильный формат (начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину)")
    @CsvSource({"011", "1110"})
    void oddLengthZeroOrEvenLengthOneTest(String number) {
        boolean result = Task8.oddLengthZeroOrEvenLengthOne(number);
        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Неправильный формат (начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину)")
    @CsvSource({"0110", "11101"})
    void oddLengthZeroOrEvenLengthOneFailedTest(String number) {
        boolean result = Task8.oddLengthZeroOrEvenLengthOne(number);
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Неверный ввод (начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину)")
    @CsvSource({"0a0", "10a1"})
    void oddLengthZeroOrEvenLengthOneWrongTest(String number) {
        boolean result = Task8.oddLengthZeroOrEvenLengthOne(number);
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Правильный формат (количество 0 кратно 3)")
    @CsvSource({"00110", "1100101"})
    void countOfZerosDivisibleByThreeTest(String number) {
        boolean result = Task8.countOfZerosDivisibleByThree(number);
        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Неправильный формат (количество 0 кратно 3)")
    @CsvSource({"001010", "10100101", "1111"})
    void countOfZerosDivisibleByThreeFailedTest(String number) {
        boolean result = Task8.countOfZerosDivisibleByThree(number);
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Неверный ввод (количество 0 кратно 3)")
    @CsvSource({"00a0", "11012010a1"})
    void countOfZerosDivisibleByThreeWrongTest(String number) {
        boolean result = Task8.oddLengthZeroOrEvenLengthOne(number);
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Правильный формат (любая строка, кроме 11 или 111)")
    @CsvSource({"0010011", "0110001"})
    void allExcept11And111Test(String number) {
        boolean result = Task8.allExcept11And111(number);
        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Неправильный формат (любая строка, кроме 11 или 111)")
    @CsvSource({"11", "111"})
    void allExcept11And111FailedTest(String number) {
        boolean result = Task8.allExcept11And111(number);
        assertFalse(result);
    }

    @ParameterizedTest
    @DisplayName("Неверный ввод (любая строка, кроме 11 или 111)")
    @CsvSource({"aa211", "3211"})
    void allExcept11And111WrongTest(String number) {
        boolean result = Task8.allExcept11And111(number);
        assertFalse(result);
    }
}
