package edu.hw1;

import java.util.Arrays;
import static edu.hw1.utils.ExceptionMessageTask3.BOTH_ARRAYS_NOT_EMPTY;

public final class Task3 {

    private Task3() {
    }

    public static boolean isNested(int[] innerArray, int[] outerArray) {
        if (innerArray == null || outerArray == null
            || innerArray.length == 0 || outerArray.length == 0) {
            throw new RuntimeException(BOTH_ARRAYS_NOT_EMPTY.getMessage());
        }

        int minInner = Arrays.stream(innerArray).min().getAsInt();
        int maxInner = Arrays.stream(innerArray).max().getAsInt();
        int minOuter = Arrays.stream(outerArray).min().getAsInt();
        int maxOuter = Arrays.stream(outerArray).max().getAsInt();

        return minInner > minOuter && maxInner < maxOuter;
    }
}
