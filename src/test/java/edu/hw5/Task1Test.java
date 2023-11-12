package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeParseException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    @Test
    @DisplayName("Тест из примера")
    void exampleTest() {
        String result = Task1.calculateAverageTimeInComputerClub(
            new String[] {
                "2022-03-12, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20"
            });
        assertEquals("3ч 40м", result);
    }

    @Test
    @DisplayName("пустой список")
    void emptyListTest() {
        String result = Task1.calculateAverageTimeInComputerClub(
            new String[] {});
        assertEquals("0ч 0м", result);
    }

    @Test
    @DisplayName("null список")
    void nullListTest() {
        String result = Task1.calculateAverageTimeInComputerClub(
            new String[] {});
        assertEquals("0ч 0м", result);
    }

    @Test
    @DisplayName("Неверное кол-во черточек")
    void wrongNumberOfDashTest() {
        Throwable thrown = catchThrowable(() -> Task1.calculateAverageTimeInComputerClub(
            new String[] {
                "2022-03-12, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 2022-04-02, 01:20"
            }));
        assertThat(thrown).hasMessage("Должна быть ровно одна черточка!");
    }

    @Test
    @DisplayName("Неправильный парсинг дат")
    void wrongParseOfDatesTest() {
        assertThrows(DateTimeParseException.class, () -> {
            Task1.calculateAverageTimeInComputerClub(
                new String[] {
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-012, 21:30 - 2022-04-02, 01:20"
                });
        });
    }
}
