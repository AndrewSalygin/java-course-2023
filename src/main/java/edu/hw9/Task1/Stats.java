package edu.hw9.Task1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Stats {
    private static final List<String> KEYS_MAP = List.of("сумма", "среднее", "максимум", "минимум");
    private static final String ERROR_MESSAGE = "Метрика не посчитана";
    String metricName;
    double[] values;
    HashMap<String, Double> calculatedMetrics;

    public Stats(String metricName, double[] values) {
        this.metricName = metricName;
        this.values = values;
    }

    public String getMetricName() {
        return metricName;
    }

    public double[] getValues() {
        return values;
    }

    public HashMap<String, Double> getCalculatedMetrics() {
        return calculatedMetrics;
    }

    public void setCalculatedMetrics(HashMap<String, Double> calculatedMetrics) {
        this.calculatedMetrics = calculatedMetrics;
    }

    public double getSum() {
        if (calculatedMetrics.containsKey(KEYS_MAP.get(0))) {
            return calculatedMetrics.get(KEYS_MAP.get(0));
        }
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }

    public double getAverage() {
        if (calculatedMetrics.containsKey(KEYS_MAP.get(1))) {
            return calculatedMetrics.get(KEYS_MAP.get(1));
        }
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }

    public double getMax() {
        if (calculatedMetrics.containsKey(KEYS_MAP.get(2))) {
            return calculatedMetrics.get(KEYS_MAP.get(2));
        }
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }

    @SuppressWarnings("MagicNumber")
    public double getMin() {
        if (calculatedMetrics.containsKey(KEYS_MAP.get(3))) {
            return calculatedMetrics.get(KEYS_MAP.get(3));
        }
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stats stats = (Stats) o;
        return Objects.equals(metricName, stats.metricName) && Arrays.equals(values, stats.values);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(metricName);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override public String toString() {
        return "Stats{"
            + "metricName='" + metricName + '\''
            + ", values=" + Arrays.toString(values)
            + ", calculatedMetrics=" + calculatedMetrics
            + '}';
    }
}
