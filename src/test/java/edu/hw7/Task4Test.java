package edu.hw7;

import edu.hw7.Task4.MonteCarlo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @Test
    @DisplayName("Расчёт числа пи последовательно методом Монте Карло")
    void calculatePiConsistentlyTest() {
        double pi = MonteCarlo.calculatePiConsistently(10_000_000);
        assertTrue(3.13 < pi && pi < 3.15);
    }

    @Test
    @DisplayName("Расчёт числа пи параллельно методом Монте Карло")
    void calculatePiParallelTest() {
        double pi = MonteCarlo.calculatePiParallel(10_000_000);
        System.out.println(pi);
        assertTrue(3.13 < pi && pi < 3.15);
    }
}
