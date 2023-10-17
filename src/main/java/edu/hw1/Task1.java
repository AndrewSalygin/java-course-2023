package edu.hw1;

import static edu.hw1.utils.ExceptionMessageTask1.FAILED_CONVERT_TIME_FORMAT;
import static edu.hw1.utils.ExceptionMessageTask1.MISSING_SIGN_IN_TIME;
import static edu.hw1.utils.ExceptionMessageTask1.TIME_FORMAT_MMSS;

public final class Task1 {

    private static final int ONE_MINUTE_IN_SECONDS = 60;

    private Task1() {
    }

    public static int minutesToSeconds(String time) {
        int minutes;
        int seconds;
        String[] timeArray = time.split(":");
        if (timeArray.length == 1) {
            throw new RuntimeException(MISSING_SIGN_IN_TIME.getMessage());
        }

        String minutesString = timeArray[0];
        String secondsString = timeArray[1];
        try {
            if (minutesString.length() < 2 || secondsString.length() != 2) {
                throw new RuntimeException(TIME_FORMAT_MMSS.getMessage());
            }
            minutes = Integer.parseInt(minutesString);
            seconds = Integer.parseInt(secondsString);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(FAILED_CONVERT_TIME_FORMAT.getMessage());
        }
        if (seconds >= ONE_MINUTE_IN_SECONDS) {
            return -1;
        }
        return minutes * ONE_MINUTE_IN_SECONDS + seconds;
    }
}
