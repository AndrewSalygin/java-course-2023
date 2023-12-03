package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.concurrent.Callable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("Вычисление факториала параллельно")
    void calculateParallelFactorialTest() {
        assertEquals(BigInteger.valueOf(3_628_800), Task2.factorial(10));
    }

    @Test
    @DisplayName("Число отрицательное")
    void negativeNumberTest() {
        Throwable thrown = catchThrowable(() -> Task2.factorial(-10));
        assertThat(thrown).hasMessage("Вводимое число отрицательное");
    }
}
