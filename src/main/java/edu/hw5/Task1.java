package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Task1 {

    public static final String PATTERN_DATE = "yyyy-MM-dd, HH:mm";

    private Task1() {

    }

    public static String calculateAverageTimeInComputerClub(String[] times) {
        Duration totalDuration = Duration.ZERO;

        if (times == null) {
            return formatDuration(totalDuration);
        }

        LocalDateTime startTime;
        LocalDateTime endTime;
        Duration duration;
        for (String time : times) {
            String[] parts = time.split(" - ");
            if (parts.length != 2) {
                throw new RuntimeException("Должна быть ровно одна черточка!");
            }
            startTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern(PATTERN_DATE));
            endTime = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern(PATTERN_DATE));

            duration = Duration.between(startTime, endTime);
            totalDuration = totalDuration.plus(duration);
        }

        int numOfSession = times.length;

        return formatDuration(numOfSession > 0 ? totalDuration.dividedBy(numOfSession) : Duration.ZERO);
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        return String.format("%dч %dм", hours, minutes);
    }
}
