package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class IntegerDataGenerator implements DataGenerator<Integer> {
    @Override
    public Integer generate(Annotation[] annotations) {
        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE;

        for (var annotation : annotations) {
            if (annotation instanceof Min min) {
                minValue = (int) min.value();
            }

            if (annotation instanceof Max max) {
                maxValue = (int) max.value();
            }
        }

        if (minValue > maxValue) {
            throw new IllegalArgumentException("Минимальное значение должно быть меньше максимального");
        }

        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }
}
