package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    @DisplayName("Увеличение счётчика параллельно")
    void atomicIncrementTest() throws InterruptedException {
        assertEquals(1000000, Task1.atomicIncrement());
    }
}
