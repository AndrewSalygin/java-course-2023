package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class CreatorFileLogReport {
    private static final String TYPE_MARKDOWN = "markdown";
    private static final String TYPE_ADOC = "adoc";
    private static final String FORMAT_DATE = "dd.MM.yyyy";
    private static final String ELEMENTS_IN_MARKDOWN_TABLE_STRING = "| %s | %d |\n";
    private static final String ELEMENTS_IN_ADOC_TABLE_STRING = "%s, %d \n";
    private final Parameters parameters;
    private final LogReport logReport;

    public CreatorFileLogReport(String[] args) {
        parameters = new Parameters(args);
        CreatorLogReport creatorLogReport = new CreatorLogReport();
        logReport = creatorLogReport.createLogReport(parameters);
    }

    public void createFile() {
        String format = parameters.getOptionalParameters().getOrDefault("--format", TYPE_MARKDOWN);
        switch (format) {
            case TYPE_MARKDOWN -> {
                createMarkdownFile();
            }
            case TYPE_ADOC -> {
                createAdocFile();
            }
            default -> throw new RuntimeException("Указан неправильный формат файла");
        }
    }

    private void createMarkdownFile() {
        String text = dataToMarkdownFormat();
        Path filePath = Paths.get("src/main/resources/project3/LogReport.md");
        try {
            Files.write(filePath, text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String dataToMarkdownFormat() {
        StringBuilder result = new StringBuilder();
        String filesName = getFilesNamesInOneLine();

        String formattedString = """
            #### Общая информация

            |Метрика|Значение|
            |:-:|-:|
            | Файл(-ы) | %s |
            | Начальная дата | %s |
            | Конечная дата | %s |
            | Количество запросов | %d |
            | Средний размер ответа | %s |

            #### Запрашиваемые ресурсы

            |Ресурс|Количество|
            |:-:|-:|
            """;
        result.append(String.format(
                formattedString,
                filesName,
                logReport.getStartDateTime().toLocalDateTime().format(
                    DateTimeFormatter.ofPattern(
                        FORMAT_DATE)),
                logReport.getEndDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern(
                    FORMAT_DATE)),
                logReport.getCountOfRequest(), logReport.getAverageSizeOfResponse()
            )
        );
        for (var entry : logReport.getResourcesSubList()) {
            result.append(String.format("|`%s`| %s |\n", entry.getKey(), entry.getValue()));
        }
        result.append("""
            #### Коды ответа

            |Код|Имя|Количество|
            |:-:|:-:|-:|
            """);
        for (var entry : logReport.getCountOfResponsesSubList()) {
            result.append(String.format(
                "| %d | %s | %d |\n",
                entry.getKey().getCode(),
                entry.getKey(),
                entry.getValue()
            ));
        }

        result.append("""

            #### Тип запроса

            |Тип запроса|Количество|
            |:-:|-:|
            """);
        for (var entry : logReport.getCountOfEachRequestSubList()) {
            result.append(String.format(
                ELEMENTS_IN_MARKDOWN_TABLE_STRING,
                entry.getKey(),
                entry.getValue()
            ));
        }

        result.append("""

            #### Ip-адреса с наибольшим количеством суммарных байт

            |Ip-адрес|Количество|
            |:-:|-:|
            """);
        for (var entry : logReport.getBytesSentByIpSubList()) {
            result.append(String.format(
                ELEMENTS_IN_MARKDOWN_TABLE_STRING,
                entry.getKey(),
                entry.getValue()
            ));
        }
        return String.valueOf(result);
    }

    private void createAdocFile() {
        String text = dataToAdocFormat();
        Path filePath = Paths.get("src/main/resources/project3/LogReport.adoc");
        try {
            Files.write(filePath, text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String dataToAdocFormat() {
        StringBuilder result = new StringBuilder();
        String filesName = getFilesNamesInOneLine();
        String formattedString = """
            ==== Общая информация
            [format="csv", options="header"]
            |===
            Файл(-ы), %s
            Начальная дата, %s
            Конечная дата, %s
            Количество запросов, %d
            Средний размер ответа, %s
            |===

            ==== Запрашиваемые ресурсы
            [format="csv", options="header"]
            |===
            Ресурс,Количество
            """;
        result.append(String.format(
                formattedString,
                filesName,
                logReport.getStartDateTime().toLocalDateTime().format(
                    DateTimeFormatter.ofPattern(
                        FORMAT_DATE)),
                logReport.getEndDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern(
                    FORMAT_DATE)),
                logReport.getCountOfRequest(), logReport.getAverageSizeOfResponse()
            )
        );
        for (Map.Entry<String, Integer> entry : logReport.getResourcesSubList()) {
            result.append(String.format("`%s`, %s \n", entry.getKey(), entry.getValue()));
        }
        result.append("""
            |===

            ==== Коды ответа
            [format="csv", options="header"]
            |===
            Код,Имя,Количество
            """);
        for (var entry : logReport.getCountOfResponsesSubList()) {
            result.append(String.format(
                "%d, %s, %d \n",
                entry.getKey().getCode(),
                entry.getKey(),
                entry.getValue()
            ));
        }

        result.append("""
            |===

            ==== Типы запросов
            [format="csv", options="header"]
            |===
            Тип запроса,Количество
            """);
        for (var entry : logReport.getCountOfEachRequestSubList()) {
            result.append(String.format(
                ELEMENTS_IN_ADOC_TABLE_STRING,
                entry.getKey(),
                entry.getValue()
            ));
        }

        result.append("""
            |===

            ==== Ip-адреса с наибольшим количеством суммарных байт
            [format="csv", options="header"]
            |===
            Ip-адрес,Количество
            """);
        for (var entry : logReport.getBytesSentByIpSubList()) {
            result.append(String.format(
                ELEMENTS_IN_ADOC_TABLE_STRING,
                entry.getKey(),
                entry.getValue()
            ));
        }
        result.append("|===\n");
        return String.valueOf(result);
    }

    private String getFilesNamesInOneLine() {
        return logReport.getFiles().stream().map(elem -> {
            if (elem.matches("^https?://\\S+$")) {
                return '`' + elem.substring(elem.lastIndexOf("/")) + '`';
            } else {
                return '`' + Paths.get(elem).getFileName().toString() + '`';
            }
        }).collect(Collectors.joining("; "));
    }
}
