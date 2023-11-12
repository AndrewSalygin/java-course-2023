package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Test {

    @ParameterizedTest
    @DisplayName("Тест из примера (правильные номера)")
    @CsvSource({"А123ВЕ777", "О777ОО177", "О777ОО64"})
    void rightNumberTest(String number) {
        boolean result = Task5.isRightLicensePlateNumber(number);
        assertTrue(result);
    }

    @ParameterizedTest
    @DisplayName("Тест из примера (неправильные номера)")
    @CsvSource({"123АВЕ777", "А123ВГ77", "А123ВЕ7777"})
    void wrongNumberTest(String number) {
        boolean result = Task5.isRightLicensePlateNumber(number);
        assertFalse(result);
    }

}
