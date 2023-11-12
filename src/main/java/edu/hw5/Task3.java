package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public final class Task3 {

    private Task3() {

    }

    public static Optional<LocalDate> parseDate(String date) {
        LocalDate result;
        try {
            result = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-[dd][d]"));
        } catch (DateTimeParseException ex) {
            try {
                result = LocalDate.parse(date, DateTimeFormatter.ofPattern("[dd][d]/[MM][M]/[yyyy][yy]"));
            } catch (DateTimeParseException innerEx) {
                return parseRelativeDate(date);
            }
        }
        return Optional.of(result);
    }

    private static Optional<LocalDate> parseRelativeDate(String date) {
        Optional<LocalDate> result = Optional.empty();

        switch (date) {
            case "tomorrow" -> {
                result = Optional.of(LocalDate.now().plusDays(1));
            }
            case "today" -> {
                result = Optional.of(LocalDate.now());
            }
            case "yesterday", "1 day ago" -> {
                result =  Optional.of(LocalDate.now().minusDays(1));
            }
            default -> {
                if (date.matches("^(\\d+ days ago)$")) {
                    result = Optional.of(LocalDate.now().minusDays(Integer.parseInt(date.split(" ")[0])));
                }
            }
        }
        return result;
    }
}
