package edu.hw5;

public final class Task7 {
    private Task7() {

    }

    public static boolean containsAtLeast3SymbolWithEndZero(String number) {
        return number.matches("^[01]{2}0[01]*$");
    }

    public static boolean sameSymbolStartAndEnd(String number) {
        return number.matches("^([01])([01]*\\1)?$");
    }

    public static boolean lengthFrom1To3(String number) {
        return number.matches("^[01]{1,3}$");
    }
}
