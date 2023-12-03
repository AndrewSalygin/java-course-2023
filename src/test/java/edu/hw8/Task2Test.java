package edu.hw8;

import edu.hw8.Task2.Fibonacci;
import edu.hw8.Task2.FixedThreadPool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("300-ый элемент последовательности Фибоначи")
    void exampleTest() {
        try (FixedThreadPool threadPool = FixedThreadPool.create(4)) {
            threadPool.start();

            for (int i = 1; i <= 300; i++) {
                int finalI = i;
                threadPool.execute(() -> Fibonacci.memoization(finalI));
            }

            Thread.sleep(100);
            BigInteger expected = new BigInteger("222232244629420445529739893461909967206666939096499764990979600");
            BigInteger actual = Fibonacci.FIB_NUMBERS.get(300);

            assertEquals(0, actual.compareTo(expected));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
