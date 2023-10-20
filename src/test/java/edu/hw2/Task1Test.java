package edu.hw2;

import static edu.hw2.Task1.Expr.*;

import edu.hw2.Task1.Expr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    @DisplayName("Тест из примера")
    void mainTest() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Expr.Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Expr.Constant(1));

        assertEquals(37, res.evaluate());
    }
}
