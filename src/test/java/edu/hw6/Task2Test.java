package edu.hw6;

import edu.hw6.Task2.ClonerFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    @Test
    @DisplayName("Клонирование файла")
    void fileCreateAndSaveTest() {
        Path path = Paths.get("src/main/resources/hw6/Task2/file.txt");
        Path clonedFilePath = Paths.get("src/main/resources/hw6/Task2/file — копия.txt");
        Path clonedFilePath2 = Paths.get("src/main/resources/hw6/Task2/file — копия (2).txt");
        ClonerFile.cloneFile(path);
        ClonerFile.cloneFile(path);
        assertTrue(Files.exists(clonedFilePath2));
        try {
            Files.delete(clonedFilePath);
            Files.delete(clonedFilePath2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
