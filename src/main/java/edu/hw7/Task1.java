package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Task1 {
    private Task1() {
    }

    @SuppressWarnings("MagicNumber")
    public static int atomicIncrement() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int numOfThreads = 10;
        List<Thread> threads = new ArrayList<>(numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    atomicInteger.incrementAndGet();
                }
            }));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return atomicInteger.get();
    }
}
