package edu.hw8.Task2;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Fibonacci {
    private Fibonacci() {}

    public static final ConcurrentHashMap<Integer, BigInteger> FIB_NUMBERS = new ConcurrentHashMap<>();

    public static BigInteger memoization(int n) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        }
        if (FIB_NUMBERS.containsKey(n)) {
            return FIB_NUMBERS.get(n);
        }

        FIB_NUMBERS.put(n, memoization(n - 1).add(memoization(n - 2)));
        return FIB_NUMBERS.get(n);
    }
}
