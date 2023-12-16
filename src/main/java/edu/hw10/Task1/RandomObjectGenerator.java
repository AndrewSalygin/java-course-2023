package edu.hw10.Task1;

import edu.hw10.Task1.annotations.NotNull;
import edu.hw10.Task1.generators.BooleanDataGenerator;
import edu.hw10.Task1.generators.ByteDataGenerator;
import edu.hw10.Task1.generators.CharacterDataGenerator;
import edu.hw10.Task1.generators.DataGenerator;
import edu.hw10.Task1.generators.DoubleDataGenerator;
import edu.hw10.Task1.generators.FloatDataGenerator;
import edu.hw10.Task1.generators.IntegerDataGenerator;
import edu.hw10.Task1.generators.LongDataGenerator;
import edu.hw10.Task1.generators.StringDataGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public final class RandomObjectGenerator {
    private final Map<Class<?>, DataGenerator<?>> generatorMap = initializeGeneratorMap();
    private final static String ERROR_STRING = "Не удалось создать параметр ";

    private Map<Class<?>, DataGenerator<?>> initializeGeneratorMap() {
        Map<Class<?>, DataGenerator<?>> map = new HashMap<>();
        map.put(int.class, new IntegerDataGenerator());
        map.put(Integer.class, new IntegerDataGenerator());
        map.put(long.class, new LongDataGenerator());
        map.put(Long.class, new LongDataGenerator());
        map.put(byte.class, new ByteDataGenerator());
        map.put(Byte.class, new ByteDataGenerator());
        map.put(double.class, new DoubleDataGenerator());
        map.put(Double.class, new DoubleDataGenerator());
        map.put(float.class, new FloatDataGenerator());
        map.put(Float.class, new FloatDataGenerator());
        map.put(boolean.class, new BooleanDataGenerator());
        map.put(Boolean.class, new BooleanDataGenerator());
        map.put(char.class, new CharacterDataGenerator());
        map.put(Character.class, new CharacterDataGenerator());
        map.put(String.class, new StringDataGenerator());
        return map;
    }

    private Object generatePrimitive(Class<?> parameterType, Annotation[] annotations) throws IllegalArgumentException {
        DataGenerator<?> dataGenerator = generatorMap.get(parameterType);

        if (dataGenerator != null) {
            return dataGenerator.generate(annotations);
        }

        if (parameterType.isAnnotationPresent(NotNull.class) || parameterType.isPrimitive()) {
            throw new IllegalArgumentException(ERROR_STRING + parameterType.getName());
        }

        return null;
    }

    public <T> T nextObject(Class<T> clazz, Class<?>... params) {
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(params);
            return generateObject(clazz, constructor);
        } catch (NoSuchMethodException e) {
            if (clazz.isRecord()) {
                constructor = ReflectionUtils.getConstructorWithMaxParameters(clazz);
                return generateObject(clazz, constructor);
            } else {
                throw new IllegalArgumentException("Не удалось найти конструктор в классе " + clazz.getName()
                    + " с указанными параметрами", e);
            }
        }
    }

    private <T> T generateObject(Class<T> clazz, Constructor<?> constructor) {
        Object[] parameters = generateRandomParameters(constructor.getParameters());
        try {
            return (T) constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось создать объект класса " + clazz.getName(), e);
        }
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethod) {
        Method method = ReflectionUtils.getMethodByName(clazz, factoryMethod);
        Object[] params = generateRandomParameters(method.getParameters());
        try {
            return clazz.cast(method.invoke(null, params));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(
                "Не удалось создать объект фабричным методом " + factoryMethod + " из класса " + clazz.getName(), e);
        }
    }

    private Object[] generateRandomParameters(Parameter[] inputParameters) {
        Object[] parameters = new Object[inputParameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = inputParameters[i].getType();
            parameters[i] = generatePrimitive(parameterType, inputParameters[i].getAnnotations());
            if (parameters[i] == null) {
                try {
                    parameters[i] = nextObject(parameterType);
                } catch (RuntimeException e) {
                    throw new RuntimeException(ERROR_STRING + parameterType.getName());
                }
            }
        }
        return parameters;
    }
}

