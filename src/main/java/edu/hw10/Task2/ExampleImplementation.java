package edu.hw10.Task2;

import edu.hw10.Task2.interfaces.ExampleInterface;

public class ExampleImplementation implements ExampleInterface {
    @Override
    @SuppressWarnings("MagicNumber")
    public long getValue() {
        return 41;
    }
}
