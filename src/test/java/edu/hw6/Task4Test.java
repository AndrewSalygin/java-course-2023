package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @Test
    @DisplayName("Создание файла")
    void createFile() {
        String path = "src/main/resources/hw6/Task4/output.txt";
        String text = "Programming is learned by writing programs. ― Brian Kernighan";
        Task4.printToFile(path, text);
        assertTrue(Files.exists(Path.of(path)));
    }

    @Test
    @DisplayName("Проверить содержимое файла")
    void checkContentFile() {
        createFile();
        String fileContent;
        try {
            Path path = Paths.get("src/main/resources/hw6/Task4/output.txt");
            byte[] fileBytes = Files.readAllBytes(path);
            fileContent = new String(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Programming is learned by writing programs. ― Brian Kernighan", fileContent.strip());
    }
}
