package edu.hw9.Task1;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class StatsCollector {
    private BlockingQueue<Stats> statsQueue;

    public StatsCollector(int capacity) {
        statsQueue = new ArrayBlockingQueue<>(capacity, true);
    }

    public void push(String metric, double[] values) {
        Stats stats = new Stats(metric, values);
        CompletableFuture.supplyAsync(() -> {
            try {
                statsQueue.put(stats);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return null;
        });
    }

    public CompletableFuture<Stats> collect() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Stats stats = statsQueue.take();
                double[] values = stats.getValues();
                double sum = 0;
                double maxValue = Double.MIN_VALUE;
                double minValue = Double.MAX_VALUE;
                for (double value : values) {
                    sum += value;
                    if (value > maxValue) {
                        maxValue = value;
                    }
                    if (value < minValue) {
                        minValue = value;
                    }
                }
                HashMap<String, Double> metricsMap = new HashMap<>();
                metricsMap.put("сумма", sum);
                metricsMap.put("среднее", sum / values.length);
                metricsMap.put("максимум", maxValue);
                metricsMap.put("минимум", minValue);
                stats.setCalculatedMetrics(metricsMap);
                return stats;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new Stats("", new double[0]);
            }
        });
    }
}
