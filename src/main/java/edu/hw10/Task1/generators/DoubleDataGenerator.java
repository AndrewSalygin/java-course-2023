package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class DoubleDataGenerator implements DataGenerator<Double> {
    @Override
    public Double generate(Annotation[] annotations) {
        double minValue = Double.MIN_VALUE;
        double maxValue = Double.MAX_VALUE;

        for (var annotation : annotations) {
            if (annotation instanceof Min min) {
                minValue = min.value();
            }

            if (annotation instanceof Max max) {
                maxValue = max.value();
            }
        }

        if (minValue > maxValue) {
            throw new IllegalArgumentException("Минимальное значение должно быть меньше максимального");
        }

        return ThreadLocalRandom.current().nextDouble(minValue, maxValue);
    }
}
