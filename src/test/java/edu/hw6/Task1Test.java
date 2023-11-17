package edu.hw6;

import edu.hw6.Task1.DiskMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {
    @Test
    @DisplayName("Создание и сохранение файла")
    void fileCreateAndSaveTest() {
        DiskMap diskMap = new DiskMap();
        diskMap.getDiskMap().put("key1", "value1");
        diskMap.getDiskMap().put("key2", "value2");

        Path path = Paths.get("src/main/resources/hw6/Task1/file.txt");
        diskMap.saveToFile(path);
        assertTrue(Files.exists(path));
    }

    @Test
    @DisplayName("Загрузка из файла")
    void fileUploadTest() {
        fileCreateAndSaveTest();
        Path path = Paths.get("src/main/resources/hw6/Task1/file.txt");
        DiskMap diskMap = new DiskMap(path);

        assertEquals(diskMap.getDiskMap(), Map.of("key1", "value1", "key2", "value2"));
    }
}
