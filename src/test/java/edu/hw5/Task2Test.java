package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("Пятницы 13 указанного года")
    void fridayOf13Test() {
        List<LocalDate> res = Task2.findFriday13ths(2024);
        List<LocalDate> expectedResult = new ArrayList<>(List.of(LocalDate.of(2024, 9, 13),
            LocalDate.of(2024, 12, 13)));
        assertEquals(expectedResult, res);
    }

    @Test
    @DisplayName("Следующая пятница 13")
    void nextFridayOf13Test() {
        LocalDate res = Task2.findNextFriday13(LocalDate.of(2024, 9, 13));
        LocalDate expectedResult = LocalDate.of(2024, 12, 13);
        assertEquals(expectedResult, res);
    }
}
