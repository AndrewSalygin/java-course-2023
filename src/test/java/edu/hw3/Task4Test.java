package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task4Test {
    @ParameterizedTest
    @DisplayName("Тесты из примера")
    @CsvSource({"2, II", "12, XII", "16, XVI"})
    void cyrillicTest(int inputNumber, String expectedString) {
        assertThat(Task4.convertToRoman(inputNumber)).isEqualTo(expectedString);
    }

    @ParameterizedTest
    @DisplayName("Граничные тесты")
    @CsvSource({"0", "-10", "4000"})
    void cyrillicTest(int inputNumber) {
        Throwable thrown = catchThrowable(() -> Task4.convertToRoman(inputNumber));
        assertThat(thrown).hasMessage("Значение должно быть от 1 до 3999.");
    }
}
