package edu.hw10.Task2.fibcalculators;

import edu.hw10.Task2.interfaces.FibCalculatorWithoutPersist;

public class FibImplementationWithoutPersist implements FibCalculatorWithoutPersist {
    @Override
    public long fib(long number) {
        if (number < 2) {
            return number;
        }
        return fib(number - 1) + fib(number - 2);
    }
}
