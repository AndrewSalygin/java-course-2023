package edu.hw10.Task2.interfaces;

import edu.hw10.Task2.cache.Cache;

public interface FibCalculatorWithPersist {
    @Cache(persist = true)
    long fib(long number);
}
