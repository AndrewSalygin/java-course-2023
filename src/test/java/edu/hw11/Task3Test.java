package edu.hw11;

import edu.hw11.Task3.FibonacciGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task3Test {
    @Test
    @DisplayName("FibonacciGenerator")
    public void task3Test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object object = FibonacciGenerator.generateFibonacciClass();
        Class<?> clazz = object.getClass();
        Method method = clazz.getMethod("fib", int.class);
        Object result = method.invoke(object, 20);
        Assertions.assertThat(result).isEqualTo(6765L);
    }
}
