package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {
    @Test
    @DisplayName("Тест из примера")
    void exampleTest() {
        boolean result = Task6.isSubsequence("abc", "achfdbaabgabcaabg");
        assertTrue(result);
    }

    @Test
    @DisplayName("Последовательность не содержит другую последовательность")
    void wrongSubsequenceTest() {
        boolean result = Task6.isSubsequence("abc", "bbb");
        assertFalse(result);
    }
}
