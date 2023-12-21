package edu.hw10.Task1.generators;

import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class StringDataGenerator implements DataGenerator<String> {

    private static final char[] ALPHABET =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int MAX_LENGTH = 10;

    @Override
    public String generate(Annotation[] annotations) {
        int minLength = 1;
        int maxLength = MAX_LENGTH;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minLength = (int) minAnnotation.value();
            }
            if (annotation instanceof Max maxAnnotation) {
                maxLength = (int) maxAnnotation.value();
            }
        }

        if (minLength > maxLength) {
            throw new IllegalArgumentException("Минимальное значение должно быть меньше максимального");
        }

        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(ALPHABET[ThreadLocalRandom.current().nextInt(0, ALPHABET.length)]);
        }
        return result.toString();
    }
}
