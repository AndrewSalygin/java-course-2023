package edu.hw1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static edu.hw1.utils.ExceptionMessageTask6.NUMBER_GREATER_1000_N_CONSIST_4_CHAR;
import static edu.hw1.utils.ExceptionMessageTask6.NUMBER_HAS_ALL_SAME_DIGITS;
import static edu.hw1.utils.ExceptionMessageTask6.WRONG_NUMBER;

public final class Task6 {

    private static final int KAPREKAR_NUMBER = 6174;

    /**
     * Единственный случай, когда нужно сохранять значимый ноль,
     * это когда в результате вычитания получается число 999.
     * Этот случай возникает, когда в числе три одной и той же цифры
     * и разница между разными цифрами равна 1.
     **/
    private static final int SAVE_ZERO = 999;
    private static final int LOWER_BOUND = 1000;
    private static final int UPPER_BOUND = 10000;
    private static Set<Integer> exceptionalNumbers = null;

    private Task6() {
    }

    public static int countK(int number) {
        if (getExceptionalNumbers().contains(number)) {
            throw new RuntimeException(NUMBER_HAS_ALL_SAME_DIGITS.getMessage());
        }
        if (number <= LOWER_BOUND || number >= UPPER_BOUND) {
            throw new RuntimeException(NUMBER_GREATER_1000_N_CONSIST_4_CHAR.getMessage());
        }

        return kaprekarRecursive(number, 0);
    }

    @SuppressWarnings("MagicNumber")
    private static Set<Integer> getExceptionalNumbers() {
        if (exceptionalNumbers == null) {
            exceptionalNumbers = new HashSet<>();
            for (int i = 1; i < 10; i++) {
                exceptionalNumbers.add(i * 1111);
            }
        }
        return exceptionalNumbers;
    }

    @SuppressWarnings("MagicNumber")
    private static int kaprekarRecursive(int number, int steps) {
        if (number == KAPREKAR_NUMBER) {
            return steps;
        }
        int desc = getDescendingOrder(number);
        int asc = getAscendingOrder(number);
        int newNumber = desc - asc;
        if (newNumber == SAVE_ZERO) {
            newNumber = SAVE_ZERO * 10;
        }
        if (newNumber == 0) {
            throw new RuntimeException(WRONG_NUMBER.getMessage());
        }

        return kaprekarRecursive(newNumber, steps + 1);
    }

    private static int getDescendingOrder(int number) {
        List<Integer> digits = getDigits(number);
        digits.sort(Comparator.reverseOrder());
        return listToNumber(digits);
    }

    private static int getAscendingOrder(int number) {
        List<Integer> digits = getDigits(number);
        digits.sort(Comparator.naturalOrder());
        return listToNumber(digits);
    }

    @SuppressWarnings("MagicNumber")
    private static List<Integer> getDigits(int number) {
        int num = number;
        List<Integer> digits = new ArrayList<>();
        while (num > 0) {
            digits.add(num % 10);
            num /= 10;
        }
        return digits;
    }

    @SuppressWarnings("MagicNumber")
    private static int listToNumber(List<Integer> digits) {
        int result = 0;
        for (int digit : digits) {
            result = result * 10 + digit;
        }
        return result;
    }
}
