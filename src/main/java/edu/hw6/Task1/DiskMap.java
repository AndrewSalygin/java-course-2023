package edu.hw6.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskMap {
    private Map<String, String> map;

    public DiskMap() {
        map = new LinkedHashMap<>();
    }

    public DiskMap(Path path) {
        map = new HashMap<>();
        loadFromFile(path);
    }

    public Map<String, String> getDiskMap() {
        return map;
    }

    public void loadFromFile(Path path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                } else {
                    throw new RuntimeException("Файл имеет неправильный формат");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(Path path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
