package edu.hw1;

public final class Task2 {

    private Task2() {
    }

    @SuppressWarnings("MagicNumber")
    public static int countNumberOfDigits(int number) {
        int counter = 1;
        int localNumber = Math.abs(number);
        while (localNumber / 10 > 0) {
            localNumber /= 10;
            counter++;
        }
        return counter;
    }
}
