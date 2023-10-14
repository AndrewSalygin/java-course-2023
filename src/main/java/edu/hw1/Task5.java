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

        boolean isSpecialPalindrom;

        String s = String.valueOf(number);
        if (s.length() < 2) {
            throw new RuntimeException(NUMBER_CONSIST_AT_LEAST_TWO_DIGITS.getMessage());
        }
        StringBuilder newS = new StringBuilder(s);

        while (newS.length() > 1) {
            isSpecialPalindrom = true;

            for (int i = 0, j = newS.length() - 1; i < j; i++, j--) {
                if (newS.charAt(i) != newS.charAt(j)) {
                    isSpecialPalindrom = false;
                    break;
                }
            }

            if (isSpecialPalindrom) {
                return isSpecialPalindrom;
            }

            for (int i = 1; i < newS.length(); i++) {
                int leftNumber = Character.getNumericValue(newS.charAt(i - 1));
                int rightNumber = Character.getNumericValue(newS.charAt(i));
                newS.replace(i - 1, i + 1, String.valueOf(leftNumber + rightNumber));
            }
        }
        return false;
    }
}
