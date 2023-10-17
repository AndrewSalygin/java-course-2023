package edu.hw1;

import static edu.hw1.utils.ExceptionMessageTask5.NUMBER_CONSIST_AT_LEAST_TWO_DIGITS;
import static edu.hw1.utils.ExceptionMessageTask5.NUMBER_NOT_NEGATIVE;

public final class Task5 {

    private Task5() {
    }

    public static boolean isSpecialPalindrome(int number) {
        if (number < 0) {
            throw new RuntimeException(NUMBER_NOT_NEGATIVE.getMessage());
        }

        String s = String.valueOf(number);
        if (s.length() < 2) {
            throw new RuntimeException(NUMBER_CONSIST_AT_LEAST_TWO_DIGITS.getMessage());
        }
        StringBuilder newS = new StringBuilder(s);

        while (newS.length() > 1) {
            if (isPalindrome(newS)) {
                return true;
            }

            for (int i = 1; i < newS.length(); i++) {
                int leftNumber = newS.charAt(i - 1) - '0';
                int rightNumber = newS.charAt(i) - '0';
                newS.replace(i - 1, i + 1, String.valueOf(leftNumber + rightNumber));
            }
        }
        return false;
    }

    public static boolean isPalindrome(StringBuilder s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
