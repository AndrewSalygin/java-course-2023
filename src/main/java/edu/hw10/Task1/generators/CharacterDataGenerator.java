package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class CharacterDataGenerator implements DataGenerator<Character> {
    @Override
    public Character generate(Annotation[] annotations) {
        char minChar = Character.MIN_VALUE;
        char maxChar = Character.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minChar = (char) minAnnotation.value();
            }
            if (annotation instanceof Max maxAnnotation) {
                maxChar = (char) maxAnnotation.value();
            }
        }

        if (minChar > maxChar) {
            throw new IllegalArgumentException("Минимальное значение должно быть меньше максимального");
        }

        return (char) ThreadLocalRandom.current().nextInt(minChar, maxChar);
    }
}

