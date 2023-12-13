package edu.hw9;

import edu.hw9.Task1.Stats;
import edu.hw9.Task1.StatsCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    @DisplayName("Тест StatsCollector")
    void statsCollectorTest() {
        StatsCollector collector = new StatsCollector(10);

        collector.push("метрика1", new double[]{3.0, 1.0, 2.0, 6.0, 8.0});

        CompletableFuture<Stats> result = collector.collect();
        result.join();
        try {
            Stats resultStats = result.get();
            assertEquals("метрика1", resultStats.getMetricName());
            assertEquals(20.0, resultStats.getSum());
            assertEquals(4.0, resultStats.getAverage());
            assertEquals(8.0, resultStats.getMax());
            assertEquals(1.0, resultStats.getMin());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
