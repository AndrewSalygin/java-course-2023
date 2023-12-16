package edu.hw10.Task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static Constructor<?> getConstructorWithMaxParameters(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("Класс " + clazz.getName() + " не имеет конструкторов");
        }

        Constructor<?> maxParametersConstructor = constructors[0];
        int maxParametersCount = maxParametersConstructor.getParameterCount();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > maxParametersCount) {
                maxParametersConstructor = constructor;
                maxParametersCount = constructor.getParameterCount();
            }
        }
        return maxParametersConstructor;
    }

    public static Method getMethodByName(Class<?> clazz, String methodName) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        throw new IllegalArgumentException("Метод " + methodName + " не найден в " + clazz.getName());
    }
}
