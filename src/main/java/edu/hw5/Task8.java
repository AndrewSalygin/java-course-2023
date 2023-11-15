package edu.hw5;

public final class Task8 {
    private Task8() {

    }

    public static boolean oddLengthSequence(String number) {
        return number.matches("^[01]([01]{2})*$");
    }

    public static boolean oddLengthZeroOrEvenLengthOne(String number) {
        return number.matches("^0([01]{2})*$|^1[01]([01]{2})*$");
    }

    public static boolean countOfZerosDivisibleByThree(String number) {
        return number.matches("^(1*01*01*01*)+$");
    }

    public static boolean allExcept11And111(String number) {
        return number.matches("^(?!11$|111$)^[0|1]*$");
    }
}
