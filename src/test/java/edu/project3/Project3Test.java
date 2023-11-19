package edu.project3;

import edu.project2.Coordinate;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Project3Test {

    @Test
    @DisplayName("Markdown")
    void markdownTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/file.txt"};
        CreatorFileLogReport creatorFileLogReport = new CreatorFileLogReport(args);
        creatorFileLogReport.createFile();

        String expectedResult = """
            #### Общая информация

            |Метрика|Значение|
            |:-:|-:|
            | Файл(-ы) | `file.txt` |
            | Начальная дата | 28.05.2015 |
            | Конечная дата | 28.05.2015 |
            | Количество запросов | 30 |
            | Средний размер ответа | 222.2 |

            #### Запрашиваемые ресурсы

            |Ресурс|Количество|
            |:-:|-:|
            |`/downloads/product_2`| 21 |
            |`/downloads/product_1`| 9 |
            #### Коды ответа

            |Код|Имя|Количество|
            |:-:|:-:|-:|
            | 404 | NOT_FOUND | 20 |
            | 304 | NOT_MODIFIED | 10 |

            #### Тип запроса

            |Тип запроса|Количество|
            |:-:|-:|
            | GET | 30 |

            #### Ip-адреса с наибольшим количеством суммарных байт

            |Ip-адрес|Количество|
            |:-:|-:|
            | 80.91.33.133 | 3034 |
            | 185.40.8.139 | 1344 |
            | 209.216.233.52 | 1272 |
            """;

        Path filePath = Paths.get("src/main/resources/project3/LogReport.md");
        String result;
        try {
            result = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Adoc")
    void adocTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/file.txt", "--format", "adoc"};
        CreatorFileLogReport creatorFileLogReport = new CreatorFileLogReport(args);
        creatorFileLogReport.createFile();

        String expectedResult = """
            ==== Общая информация
            [format="csv", options="header"]
            |===
            Файл(-ы), `file.txt`
            Начальная дата, 28.05.2015
            Конечная дата, 28.05.2015
            Количество запросов, 30
            Средний размер ответа, 222.2
            |===

            ==== Запрашиваемые ресурсы
            [format="csv", options="header"]
            |===
            Ресурс,Количество
            `/downloads/product_2`, 21\s
            `/downloads/product_1`, 9\s
            |===

            ==== Коды ответа
            [format="csv", options="header"]
            |===
            Код,Имя,Количество
            404, NOT_FOUND, 20\s
            304, NOT_MODIFIED, 10\s
            |===

            ==== Типы запросов
            [format="csv", options="header"]
            |===
            Тип запроса,Количество
            GET, 30\s
            |===

            ==== Ip-адреса с наибольшим количеством суммарных байт
            [format="csv", options="header"]
            |===
            Ip-адрес,Количество
            80.91.33.133, 3034\s
            185.40.8.139, 1344\s
            209.216.233.52, 1272\s
            |===
            """;

        Path filePath = Paths.get("src/main/resources/project3/LogReport.adoc");
        String result;
        try {
            result = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Указан неправильный формат отчёта")
    void wrongFormatLogReportTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/file.txt", "--format", "1234"};
        CreatorFileLogReport creatorFileLogReport = new CreatorFileLogReport(args);
        Throwable thrown = catchThrowable(creatorFileLogReport::createFile);
        assertThat(thrown).hasMessage("Указан неправильный формат файла");
    }

    @Test
    @DisplayName("Указан неправильный формат записи в логах")
    void wrongFormatRecordInLogsTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/wrongFile.txt"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Неверный формат записи: 44480.91.33.133 - - [28/May/2015:10:05:52 +0000] " +
                "\"GET /downloads/product_1 HTTP/1.1\" 404 340 \"-\" " +
                "\"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"");
    }

    @Test
    @DisplayName("Запрос первым должен содержать обязательный параметр --path.")
    void firstPathParameterTest() {
        String[] args = new String[] {"123", "--path", "src/main/resources/project3/file.txt"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .hasMessage("Запрос первым должен содержать обязательный параметр --path.");
    }

    @Test
    @DisplayName("Параметр --path может указываться ровно 1 раз")
    void twicePathParameterTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/file.txt", "--path",
            "src/main/resources/project3/wrongFile.txt"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .hasMessage("Параметр --path может указываться ровно 1 раз");
    }

    @Test
    @DisplayName("Параметр --path должен содержать как минимум 1 аргумент")
    void oneArgumentPathParameterTest() {
        String[] args = new String[] {"--path"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .hasMessage("Параметр --path должен содержать как минимум 1 аргумент.");
    }

    @Test
    @DisplayName("Необязательные параметры могут указываться только один раз")
    void onlyOneTimeParameterTest() {
        String[] args = new String[] {"--path", "src/main/resources/project3/file.txt", "--from", "25.05.2015", "--to",
            "26.05.2015", "--from", "25.05.2015"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .hasMessage("Параметр --from может указываться только один раз.");
    }

    @Test
    @DisplayName("Необязательные параметры не могут иметь более одного аргумента")
    void onlyOneTimeArgumentTest() {
        String[] args =
            new String[] {"--path", "src/main/resources/project3/file.txt", "--from", "25.05.2015", "26.05.2015"};
        assertThatThrownBy(() -> new CreatorFileLogReport(args).createFile())
            .hasMessage("У параметра --from не может быть больше одного аргумента.");
    }

}
