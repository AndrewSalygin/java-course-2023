package edu.project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parameters {

    private static final String PATH_STRING = "--path";
    private final List<String> paths;
    private final Map<String, String> optionalParameters;

    public List<String> getPaths() {
        return paths;
    }

    public Map<String, String> getOptionalParameters() {
        return optionalParameters;
    }

    public Parameters(String[] args) {
        // Проверки обязательного параметра --path
        if (!args[0].equals(PATH_STRING)) {
            throw new RuntimeException("Запрос первым должен содержать обязательный параметр --path.");
        }
        if (Arrays.stream(args).filter(element -> element.equals(PATH_STRING)).count() != 1) {
            throw new RuntimeException("Параметр --path может указываться ровно 1 раз");
        }

        Set<String> possibleParameters = Set.of("--from", "--to", "--format");
        paths = new ArrayList<>();
        int i = 1;
        // Загружаем список путей до тех пор, пока не встретится ключевое слово (параметр)
        while (i < args.length && !possibleParameters.contains(args[i])) {
            paths.add(args[i++]);
        }
        // Аргументов для путей нет
        if (paths.size() == 0) {
            throw new RuntimeException("Параметр --path должен содержать как минимум 1 аргумент.");
        }
        optionalParameters = new HashMap<>();
        if (i < args.length) {
            String currentParameter = args[i];
            // Заполняем оставшиеся параметры
            for (; i < args.length; i++) {
                if (possibleParameters.contains(args[i])) {
                    if (optionalParameters.containsKey(args[i])) {
                        throw new RuntimeException("Параметр " + args[i] + " может указываться только один раз.");
                    } else {
                        currentParameter = args[i];
                    }
                } else { // Условие для аргументов параметра
                    if (!optionalParameters.containsKey(currentParameter)) {
                        optionalParameters.put(currentParameter, args[i]);
                    } else {
                        throw new RuntimeException(
                            "У параметра " + currentParameter + " не может быть больше одного аргумента.");
                    }
                }
            }
        }
    }
}
