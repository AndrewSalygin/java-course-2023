package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {
    @Test
    @DisplayName("Длина строки не менее 3 и третий символ 0")
    void lengthMoreThan3AndZeroThirdTest() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("1101");
        assertTrue(result);
    }

    @Test
    @DisplayName("Длина строки менее 3")
    void lengthLessThan3Test() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("11");
        assertFalse(result);
    }

    @Test
    @DisplayName("Длина строки больше 3, но третий символ не 0")
    void lengthMoreThan3AndZeroNotThirdTest() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("111");
        assertFalse(result);
    }

    @Test
    @DisplayName("Неправильная строка (метод не менее 3 символа)")
    void incorrectSequenceThreeSymbolsMethodTest() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("110b1");
        assertFalse(result);
    }

    @Test
    @DisplayName("Строка начинается и заканчивается одним и тем же символом")
    void sameSymbolStartAndEndTest() {
        boolean result = Task7.sameSymbolStartAndEnd("1101");
        assertTrue(result);
    }

    @Test
    @DisplayName("Строка начинается и заканчивается не одним и тем же символом")
    void notSameSymbolStartAndEndTest() {
        boolean result = Task7.sameSymbolStartAndEnd("1100");
        assertFalse(result);
    }

    @Test
    @DisplayName("Неправильная строка (метод заканчивается одним и тем же символом)")
    void incorrectSequenceSameMethodTest() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("110b1");
        assertFalse(result);
    }

    @Test
    @DisplayName("Длина строки от 1 до 3")
    void lengthStringFrom1To3Test() {
        boolean result = Task7.lengthFrom1To3("111");
        assertTrue(result);
    }

    @Test
    @DisplayName("Длина меньше 1")
    void lengthStringLess1Test() {
        boolean result = Task7.lengthFrom1To3("");
        assertFalse(result);
    }

    @Test
    @DisplayName("Длина больше 3")
    void lengthStringMore3Test() {
        boolean result = Task7.lengthFrom1To3("1111");
        assertFalse(result);
    }

    @Test
    @DisplayName("Неправильная строка (строка от 1 до 3 символов)")
    void incorrectSequenceFrom1To3MethodTest() {
        boolean result = Task7.containsAtLeast3SymbolWithEndZero("0b1");
        assertFalse(result);
    }
}
