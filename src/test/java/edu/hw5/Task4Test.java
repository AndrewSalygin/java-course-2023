package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {

    @Test
    @DisplayName("Пароль состоит из как минимум одного специального символа")
    void passwordConsistsSpecifiedSymbolTest() {
        boolean result = Task4.isConsistOfAnySpecifiedSymbol("3few*^&QRF");
        assertTrue(result);
    }

    @Test
    @DisplayName("Пароль не состоит из как минимум одного специального символа")
    void passwordNotConsistsSpecifiedSymbolTest() {
        boolean result = Task4.isConsistOfAnySpecifiedSymbol("gewger323r51");
        assertFalse(result);
    }

}
