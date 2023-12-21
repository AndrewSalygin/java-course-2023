package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class LongDataGenerator implements DataGenerator<Long> {
    @Override
    public Long generate(Annotation[] annotations) {
        long minValue = Long.MIN_VALUE;
        long maxValue = Long.MAX_VALUE;

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

        return ThreadLocalRandom.current().nextLong(minValue, maxValue);
    }
}
