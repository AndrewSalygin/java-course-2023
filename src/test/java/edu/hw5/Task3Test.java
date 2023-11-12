package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
    @ParameterizedTest
    @MethodSource("provideDataForExampleTest")
    @DisplayName("Тест из примера")
    void exampleTest(String inputDate, LocalDate expectedDate) {
        assertEquals(Optional.of(expectedDate), Task3.parseDate(inputDate));
    }

    static Stream<Arguments> provideDataForExampleTest() {
        return Stream.of(
            Arguments.of("2020-10-10",  LocalDate.of(2020, 10, 10)),
            Arguments.of("2020-12-2",  LocalDate.of(2020, 12, 2)),
            Arguments.of("1/3/1976",  LocalDate.of(1976, 3, 1)),
            Arguments.of("1/3/20",  LocalDate.of(2020, 3, 1)),
            Arguments.of("tomorrow",  LocalDate.now().plusDays(1)),
            Arguments.of("today",  LocalDate.now()),
            Arguments.of("yesterday",  LocalDate.now().minusDays(1)),
            Arguments.of("1 day ago",  LocalDate.now().minusDays(1)),
            Arguments.of("2234 days ago",  LocalDate.now().minusDays(2234))
        );
    }

    @Test
    @DisplayName("Неправильный формат даты")
    void failedTest() {
        assertEquals(Optional.empty(), Task3.parseDate("2002-233-1"));
    }
}
