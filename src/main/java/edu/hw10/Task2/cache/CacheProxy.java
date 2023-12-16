package edu.hw10.Task2.cache;

import edu.hw10.Task2.serializers.LongSerializer;
import edu.hw10.Task2.serializers.Serializer;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public final class CacheProxy {
    private CacheProxy() {
    }

    private static final Map<Class<?>, Serializer<?>> SERIALIZER_MAP = new HashMap<>();

    // Создание прокси-объекта
    public static <T> T create(T object, Path path) {
        SERIALIZER_MAP.put(Long.class, new LongSerializer());
        SERIALIZER_MAP.put(long.class, new LongSerializer());
        return createProxy(object, path);
    }

    public static <T> T create(T object) {
        SERIALIZER_MAP.put(Long.class, new LongSerializer());
        SERIALIZER_MAP.put(long.class, new LongSerializer());
        return createProxy(object);
    }

    private static <T> T createProxy(T object, Path path) {
        return (T) Proxy.newProxyInstance(
            object.getClass().getClassLoader(),
            object.getClass().getInterfaces(),
            new CacheInvocationHandler<>(object, path)
        );
    }

    private static <T> T createProxy(T object) {
        return (T) Proxy.newProxyInstance(
            object.getClass().getClassLoader(),
            object.getClass().getInterfaces(),
            new CacheInvocationHandler<>(object)
        );
    }

    private final static class CacheInvocationHandler<T> implements InvocationHandler {
        // Хранение кэшированных значений
        private final Map<Method, Map<List<Object>, Object>> cachedValues = new HashMap<>();

        // Объект, для которого выполняется кэширование
        private final T cachingObject;

        // Путь кэширования в файловой системе
        private final Path path;

        private CacheInvocationHandler(T cachingObject, Path path) {
            this.cachingObject = cachingObject;
            this.path = path;
        }

        private CacheInvocationHandler(T cachingObject) {
            this.cachingObject = cachingObject;
            this.path = null;
        }

        // Метод, обрабатывающий вызовы методов прокси-объекта
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Cache.class)) {
                Cache cache = method.getAnnotation(Cache.class);
                if (cache.persist()) {
                    return computePersistCache(method, args);
                }
                return computeCache(method, args);
            }
            return method.invoke(cachingObject, args);
        }

        private Object computeCache(Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
            // Преобразование аргументов в список, который будет использоваться в качестве ключа кэша
            List<Object> argsList;
            if (args != null) {
                argsList = Arrays.asList(args);
            } else {
                argsList = null;
            }

            // Если кэш для данного метода еще не создан, создаем его
            if (!cachedValues.containsKey(method)) {
                cachedValues.put(method, new HashMap<>());
            }

            // Если для данного метода и аргументов нет значения в кэше, вычисляем результат и добавляем в кэш
            var values = cachedValues.get(method);
            if (!values.containsKey(argsList)) {
                Object result = method.invoke(cachingObject, args);
                values.put(argsList, result);
                return result;
            }

            // Если значение уже есть в кэше, возвращаем его
            return values.get(argsList);
        }

        // Метод для выполнения кэширования в файловой системе
        private Object computePersistCache(Method method, Object[] args) {
            // Получаем сериализатор для результата метода
            Serializer<?> serializer = SERIALIZER_MAP.get(method.getReturnType());

            if (serializer == null) {
                throw new IllegalArgumentException(
                    "Не найдено необходимого сериализатора для типа: " + method.getReturnType());
            }

            // Если метод не принимает аргументов, выполняем кэширование без аргументов
            if (args == null || args.length == 0) {
                return cacheWithNoArgs(method, args, serializer);
            } else {
                return cacheWithArgs(method, args, serializer);
            }
        }

        private Object cacheWithNoArgs(Method method, Object[] args, Serializer<?> serializer) {
            // Если файл для данного метода существует, десериализуем результат из файла
            if (Files.exists(path.resolve(method.getName()))) {
                try {
                    return serializer.deserialize(Files.readString(path.resolve(method.getName())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    // Если файла нет, сериализуем и сохраняем в файл
                    Object result = method.invoke(cachingObject, args);
                    Files.writeString(path.resolve(method.getName()), serializer.serialize(result));
                    return result;
                } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private Object cacheWithArgs(Method method, Object[] args, Serializer<?> serializer) {
            StringBuilder arguments = new StringBuilder();
            for (Object arg : args) {
                // Получаем свой сериализатор для каждого класса (из добавленных)
                Serializer<?> argumentSerializer = SERIALIZER_MAP.get(arg.getClass());

                if (argumentSerializer == null) {
                    throw new IllegalArgumentException("Класс " + arg.getClass() + "не поддерживаем для сериализации");
                }

                arguments.append(argumentSerializer.serialize(arg)).append(';');
            }
            String serializedArguments = arguments.toString();
            String fileName = getFileName(method);

            Properties properties = new Properties();
            if (Files.exists(path.resolve(fileName))) {
                try {
                    properties.load(Files.newBufferedReader(path.resolve(fileName)));

                    // Если в properties есть значение для текущих аргументов, возвращаем его
                    if (properties.containsKey(serializedArguments)) {
                        return serializer.deserialize(properties.getProperty(serializedArguments));
                    }

                    // Если нет, то сохраняем
                    return calculateAndSaveCache(method, args, serializer, properties, serializedArguments);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return calculateAndSaveCache(method, args, serializer, properties, serializedArguments);
            }
        }

        private Object calculateAndSaveCache(
            Method method,
            Object[] args,
            Serializer<?> serializer,
            Properties properties,
            String serializedArguments
        ) {
            try {
                Object result = method.invoke(cachingObject, args);
                properties.put(serializedArguments, serializer.serialize(result));
                properties.store(Files.newBufferedWriter(path.resolve(getFileName(method))), null);
                return result;
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String getFileName(Method method) {
            return method.getName() + Arrays.stream(method.getParameters())
                .map(param -> "_" + param.getType().getName())
                .collect(Collectors.joining());
        }

    }
}
