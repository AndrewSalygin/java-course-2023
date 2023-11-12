package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public final class Task2 {

    private Task2() {

    }

    @SuppressWarnings("MagicNumber")
    public static List<LocalDate> findFriday13ths(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Переданный год должен быть положительным");
        }

        List<LocalDate> friday13List = new ArrayList<>();

        for (int mounth = 1; mounth <= 12; mounth++) {
            LocalDate tmpDay = LocalDate.of(year, mounth, 13);
            if (tmpDay.getDayOfWeek() == DayOfWeek.FRIDAY) {
                friday13List.add(tmpDay);
            }
        }

        return friday13List;
    }

    @SuppressWarnings("MagicNumber")
    public static LocalDate findNextFriday13(LocalDate currentDate) {
        LocalDate nextFriday13 = currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        while (nextFriday13.getDayOfMonth() != 13) {
            nextFriday13 = nextFriday13.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }

        return nextFriday13;
    }
}
