package edu.hw3;

public final class Task4 {
    private Task4() {}

    private static final String[] ROMAN_VALUES = {
        "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };
    private static final int[] ARABIC_VALUES = {
        1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    private static final int MAX_ROMAN_NUMBER = 3999;
    private static final int MIN_ROMAN_NUMBER = 1;

    public static String convertToRoman(int num) {
        int number = num;
        if (number < MIN_ROMAN_NUMBER || number > MAX_ROMAN_NUMBER) {
            throw new IllegalArgumentException("Значение должно быть от 1 до 3999.");
        }

        StringBuilder roman = new StringBuilder();
        int i = 0;
        while (number > 0) {
            if (number >= ARABIC_VALUES[i]) {
                roman.append(ROMAN_VALUES[i]);
                number -= ARABIC_VALUES[i];
            } else {
                i++;
            }
        }
        return roman.toString();
    }
}
