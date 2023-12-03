package edu.hw8.Task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final int countOfThreads;
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;

    private FixedThreadPool(int countOfThreads) {
        this.countOfThreads = countOfThreads;
        threads = new Thread[countOfThreads];
        taskQueue = new LinkedBlockingQueue<>();
    }

    public static FixedThreadPool create(int countOfThreads) {
        return new FixedThreadPool(countOfThreads);
    }

    @Override
    public void start() {
        for (int i = 0; i < countOfThreads; i++) {
            threads[i] = new Thread(() -> {
                try {
                    while (true) {
                        Runnable task = taskQueue.take();
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            taskQueue.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
