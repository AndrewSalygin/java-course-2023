package edu.hw11.Task3;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.Implementation;

public final class FibonacciGenerator {
    private FibonacciGenerator() {
    }

    public static Object generateFibonacciClass() {
        try (var unloaded = new ByteBuddy()
            .subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", long.class, Ownership.MEMBER, Visibility.PUBLIC)
            .withParameter(int.class, "n")
            .intercept(createFibonacciImplementation())
            .make()
        ) {
            return unloaded.load(FibonacciGenerator.class.getClassLoader()).getLoaded().getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Implementation createFibonacciImplementation() {
        return new Implementation.Simple(new FibonacciByteCodeAppender());
    }
}
