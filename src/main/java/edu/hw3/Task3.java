package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Task3 {
    private Task3() {}

    public static <T> Map<T, Integer> freqDist(List<T> words) {
        HashMap<T, Integer> result = new HashMap<>();
        for (T word : words) {
            result.put(word, result.getOrDefault(word, 0) + 1);
        }
        return result;
    }
}
