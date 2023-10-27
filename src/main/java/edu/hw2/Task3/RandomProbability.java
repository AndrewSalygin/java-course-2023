package edu.hw2.Task3;

import java.util.Random;

public final class RandomProbability {
    private static final Random RANDOM = new Random();

    static boolean attempt(double probability) {
        return RANDOM.nextDouble() < probability;
    }

    private RandomProbability() {}
}
