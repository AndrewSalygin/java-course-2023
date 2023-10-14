package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task7Test {
    @Test
    @DisplayName("Число равно 0 в сдвиге влево")
    void numberEqualsZeroShiftLeftTest() {
        Throwable thrown = catchThrowable(() -> Task7.rotateLeft(0, 3));
        assertThat(thrown).hasMessage("Число должно быть только положительным целым.");
    }

    @Test
    @DisplayName("Число равно 0 в сдвиге вправо")
    void numberEqualsZeroShiftRightTest() {
        Throwable thrown = catchThrowable(() -> Task7.rotateRight(0, 3));
        assertThat(thrown).hasMessage("Число должно быть только положительным целым.");
    }

    @Test
    @DisplayName("Отрицательное число в сдвиге влево")
    void negativeNumberShiftLeftTest() {
        Throwable thrown = catchThrowable(() -> Task7.rotateLeft(-50, 3));
        assertThat(thrown).hasMessage("Число должно быть только положительным целым.");
    }

    @Test
    @DisplayName("Отрицательное число в сдвиге вправо")
    void negativeNumberShiftRightTest() {
        Throwable thrown = catchThrowable(() -> Task7.rotateRight(-50, 3));
        assertThat(thrown).hasMessage("Число должно быть только положительным целым.");
    }

    @Test
    @DisplayName("Отрицательный сдвиг влево")
    void naturalNumberShiftLeftNegativeTest() {
        int result = Task7.rotateLeft(20, -3);
        assertThat(result).isEqualTo(18);
    }

    @Test
    @DisplayName("Отрицательный сдвиг вправо")
    void naturalNumberShiftRightNegativeTest() {
        int result = Task7.rotateRight(20, -3);
        assertThat(result).isEqualTo(5);
    }

    @ParameterizedTest
    @DisplayName("Сдвиг положительного числа влево")
    @CsvSource({"22, 1, 13", "16, 1, 1", "17, 2, 6"})
    void naturalNumberShiftLeftTest(int number, int shift, int expectedResult) {
        int result = Task7.rotateLeft(number, shift);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Сдвиг положительного числа вправо")
    @CsvSource({"22, 1, 11", "8, 1, 4"})
    void naturalNumberShiftRightTest(int number, int shift, int expectedResult) {
        int result = Task7.rotateRight(number, shift);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Сдвиг положительного числа влево (на количество цифр в двоичной записи)")
    void naturalNumberShiftLeftFullCycleTest() {
        int result = Task7.rotateLeft(22, 5);
        assertThat(result).isEqualTo(22);
    }

    @Test
    @DisplayName("Сдвиг положительного числа вправо (на количество цифр в двоичной записи)")
    void naturalNumberShiftRightFullCycleTest() {
        int result = Task7.rotateRight(22, 5);
        assertThat(result).isEqualTo(22);
    }
}
