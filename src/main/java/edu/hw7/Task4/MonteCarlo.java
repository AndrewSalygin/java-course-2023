package edu.hw7.Task4;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class MonteCarlo {
    private final static int COUNT_OF_THREADS = 2;

    private MonteCarlo() {
    }

    @SuppressWarnings("MagicNumber")
    public static double calculatePiConsistently(long n) {
        long circleCount = 0;
        double x;
        double y;
        for (int i = 0; i < n; i++) {
            x = ThreadLocalRandom.current().nextDouble();
            y = ThreadLocalRandom.current().nextDouble();
            if (Math.sqrt(x * x + y * y) <= 1) {
                circleCount++;
            }
        }
        return 4.0 * circleCount / n;
    }

    @SuppressWarnings("MagicNumber")
    public static double calculatePiParallel(long n) {
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT_OF_THREADS);
        long countOfIterationForThreads = n / COUNT_OF_THREADS;

        Callable<Long> task = () -> {
            long circleCountLocal = 0;
            double x;
            double y;
            for (int j = 0; j < countOfIterationForThreads; j++) {
                x = ThreadLocalRandom.current().nextDouble();
                y = ThreadLocalRandom.current().nextDouble();
                if (Math.sqrt(x * x + y * y) <= 1) {
                    circleCountLocal++;
                }
            }
            return circleCountLocal;
        };
        List<Callable<Long>> tasks = Stream.generate(() -> task).limit(COUNT_OF_THREADS).toList();

        long circleCount = 0;
        try {
            circleCount = executorService.invokeAll(tasks)
                .stream()
                .mapToLong(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return 4.0 * circleCount / n;
    }
}
