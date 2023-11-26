package edu.hw7;

import java.math.BigInteger;
import java.util.stream.LongStream;

public final class Task2 {
    private Task2() {
    }

    public static BigInteger factorial(int number) {
        if (number >= 0) {
            return LongStream.rangeClosed(1, number)
                .mapToObj(BigInteger::valueOf)
                .parallel().reduce(BigInteger.ONE, BigInteger::multiply);
        } else {
            throw new RuntimeException("Вводимое число отрицательное");
        }
    }
}
