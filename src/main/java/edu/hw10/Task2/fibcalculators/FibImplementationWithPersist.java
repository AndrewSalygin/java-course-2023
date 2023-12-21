package edu.hw10.Task2.fibcalculators;

import edu.hw10.Task2.interfaces.FibCalculatorWithPersist;

public class FibImplementationWithPersist implements FibCalculatorWithPersist {
    @Override
    public long fib(long number) {
        if (number < 2) {
            return number;
        }
        return fib(number - 1) + fib(number - 2);
    }
}
