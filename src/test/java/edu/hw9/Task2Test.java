package edu.hw9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import static edu.hw9.Task2.ParallelFileSystemProcessor.findDirectoriesWithManyFiles;
import static edu.hw9.Task2.ParallelFileSystemProcessor.findFilesMatchingPredicate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {

    @Test
    @DisplayName("Директории с k файлами")
    void isMoreKFilesInDirectoryTest() {
        String rootPath = "src/main/resources/hw9/Task2";
        int k = 2;
        List<Path> resultPaths = findDirectoriesWithManyFiles(rootPath, k);
        List<Path> expectedPaths = List.of(
            Paths.get("src/main/resources/hw9/Task2/folder1"),
            Paths.get("src/main/resources/hw9/Task2")
        );

        assertEquals(expectedPaths.size(), resultPaths.size());
        assertTrue(expectedPaths.containsAll(resultPaths));
        assertTrue(resultPaths.containsAll(expectedPaths));
    }

    @Test
    @DisplayName("Поиск файлов по предикату")
    void searchFilesByPredicateTest() {
        String rootPath = "src/main/resources/hw9/Task2";
        List<Path> expectedPaths = List.of(
            Paths.get("src/main/resources/hw9/Task2/file1.txt"),
            Paths.get("src/main/resources/hw9/Task2/file2.txt"),
            Paths.get("src/main/resources/hw9/Task2/file3.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder1/file4.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder1/file5.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder1/file9.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder1/folder4/file6.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder2/file7.txt"),
            Paths.get("src/main/resources/hw9/Task2/folder2/folder5/folder6/file8.txt")
        );
        Predicate<File> isTxtFile = file -> file.isFile() && file.getName().toLowerCase().endsWith(".txt");
        List<Path> resultPaths = findFilesMatchingPredicate(rootPath, isTxtFile);

        assertEquals(expectedPaths.size(), resultPaths.size());
        assertTrue(expectedPaths.containsAll(resultPaths));
        assertTrue(resultPaths.containsAll(expectedPaths));
    }
}
