package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task1Test {
    @Test
    @DisplayName("Секунд больше или равно 60")
    void secondMore60Test() {
        int result = Task1.minutesToSeconds("10:70");
        assertThat(result).isEqualTo(-1);
    }

    @ParameterizedTest
    @DisplayName("Секунд меньше 60")
    @CsvSource({"10:30, 630", "01:00, 60", "13:56, 836"})
    void secondLess60Test(String time, int expectedResult) {
        int result = Task1.minutesToSeconds(time);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Время равно 0")
    void timeEqualsZeroTest() {
        int result = Task1.minutesToSeconds("00:00");
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Отсутствует знак ':' во времени")
    void noColonCharacterTest() {
        Throwable thrown = catchThrowable(() -> {
            Task1.minutesToSeconds("3252");
        });
        assertThat(thrown).hasMessage("Отсутствует знак ':' во времени.");
    }

    @Test
    @DisplayName("Введена неверная строка (состоит из букв)")
    void wrongStringTest() {
        Throwable thrown = catchThrowable(() -> {
            Task1.minutesToSeconds("te:st");
        });
        assertThat(thrown).hasMessage("Неудачная попытка преобразования времени в численный формат.");
    }

    @Test
    @DisplayName("Неправильный формат времени")
    void wrongFormatTimeTest() {
        Throwable thrown = catchThrowable(() -> {
            Task1.minutesToSeconds("0:001");
        });
        assertThat(thrown).hasMessage("Время должно быть введено в формате mm:ss.");
    }
}
