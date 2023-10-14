package edu.hw1;

import static edu.hw1.utils.ExceptionMessageTask7.NUMBER_MUST_BE_POSITIVE;

public final class Task7 {

    private Task7() {
    }

    public static int rotateLeft(int n, int shift) {
        if (n <= 0) {
            throw new RuntimeException(NUMBER_MUST_BE_POSITIVE.getMessage());
        }
        if (shift < 0) {
            return rotateRight(n, Math.abs(shift));
        }
        String s = Integer.toBinaryString(n);
        int amountOfShift = shift % s.length();
        String result = s.substring(amountOfShift) + s.substring(0, amountOfShift);
        return Integer.parseInt(result, 2);
    }

    public static int rotateRight(int n, int shift) {
        if (n <= 0) {
            throw new RuntimeException(NUMBER_MUST_BE_POSITIVE.getMessage());
        }
        if (shift < 0) {
            return rotateLeft(n, Math.abs(shift));
        }
        String s = Integer.toBinaryString(n);
        int amountOfShift = shift % s.length();
        String result = s.substring(s.length() - amountOfShift) + s.substring(0, s.length() - amountOfShift);
        return Integer.parseInt(result, 2);
    }
}
