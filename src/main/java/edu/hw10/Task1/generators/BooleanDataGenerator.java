package edu.hw10.Task1.generators;

import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class BooleanDataGenerator implements DataGenerator<Boolean> {
    @Override
    public Boolean generate(Annotation[] annotations) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
