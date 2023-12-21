package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class FloatDataGenerator implements DataGenerator<Float> {
    @Override
    public Float generate(Annotation[] annotations) {
        float minValue = Float.MIN_VALUE;
        float maxValue = Float.MAX_VALUE;

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

        return ThreadLocalRandom.current().nextFloat(minValue, maxValue);
    }
}
