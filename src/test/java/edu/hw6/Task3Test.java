package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static edu.hw6.Task3.AbstractFilter.READABLE;
import static edu.hw6.Task3.AbstractFilter.REGULAR_FILE;
import static edu.hw6.Task3.AbstractFilter.WRITABLE;
import static edu.hw6.Task3.AbstractFilter.globMatches;
import static edu.hw6.Task3.AbstractFilter.largerThan;
import static edu.hw6.Task3.AbstractFilter.magicNumber;
import static edu.hw6.Task3.AbstractFilter.regexContains;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
    public static Path dir = Paths.get("src/main/resources/hw6/Task3");

    @Test
    @DisplayName("Тест из примера")
    void exampleTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE
            .and(READABLE)
            .and(largerThan(100))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("[-]"));

        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(List.of(Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")), result);
    }

    @Test
    @DisplayName("regularFile фильтр")
    void regularFileTest() {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, REGULAR_FILE)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(
                Paths.get("src/main/resources/hw6/Task3/file.txt"),
                Paths.get("src/main/resources/hw6/Task3/java2.png"),
                Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")
            ),
            result
        );
    }

    @Test
    @DisplayName("readable фильтр")
    void readableTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(READABLE);
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(
                Paths.get("src/main/resources/hw6/Task3/file.txt"),
                Paths.get("src/main/resources/hw6/Task3/java2.png"),
                Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")
            ),
            result
        );
    }

    @Test
    @DisplayName("writable фильтр")
    void writableTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(WRITABLE);
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(
                Paths.get("src/main/resources/hw6/Task3/file.txt"),
                Paths.get("src/main/resources/hw6/Task3/java2.png"),
                Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")
            ),
            result
        );
    }

    @Test
    @DisplayName("largerThan фильтр")
    void largerThanTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(largerThan(8));
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(
                Paths.get("src/main/resources/hw6/Task3/java2.png"),
                Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")
            ),
            result
        );
    }

    @Test
    @DisplayName("magicNumber фильтр")
    void magicNumberTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(magicNumber(0x89, 'P', 'N', 'G'));
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(
                Paths.get("src/main/resources/hw6/Task3/java2.png"),
                Paths.get("src/main/resources/hw6/Task3/tinkoff-logo.png")
            ),
            result
        );
    }

    @Test
    @DisplayName("globMatches фильтр")
    void globMatchesTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(globMatches("*.txt"));
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(Paths.get("src/main/resources/hw6/Task3/file.txt")),
            result
        );
    }

    @Test
    @DisplayName("Фильтр regexContains прошел")
    void regexContainsTest() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE.and(regexContains("\\d"));
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(result::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(
            List.of(Paths.get("src/main/resources/hw6/Task3/java2.png")),
            result
        );
    }
}
