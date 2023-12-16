package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    public void task1Test() {
        Class<?> dynamicClass = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(Task1Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Constructor<?> constructor;
        try {
            constructor = dynamicClass.getConstructor();
            Object result = constructor.newInstance();
            assertEquals(result.toString(), "Hello, ByteBuddy!");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
