package edu.project1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;

    public Session(String answer, char[] userAnswer, int maxAttempts, int attempts) {
        this.answer = answer;
        this.userAnswer = userAnswer;
        this.maxAttempts = maxAttempts;
        this.attempts = attempts;
    }

    @NotNull GuessResult guess(char guess) {
        if (answer.indexOf(guess) == -1) {
            if (attempts + 1 == maxAttempts) {
                return new GuessResult.Defeat(userAnswer, ++attempts, maxAttempts);
            }
            return new GuessResult.FailedGuess(userAnswer, ++attempts, maxAttempts);
        }
        HashSet<Integer> indexes = (HashSet<Integer>) IntStream.range(0, answer.length())
            .filter(i -> answer.charAt(i) == guess)
            .boxed().collect(Collectors.toSet());
        for (int index : indexes) {
            userAnswer[index] = guess;
        }

        if (Arrays.equals(userAnswer, answer.toCharArray())) {
            return new GuessResult.Win(userAnswer, attempts, maxAttempts);
        }
        return new GuessResult.SuccessfulGuess(userAnswer, attempts, maxAttempts);
    }

    @NotNull GuessResult giveUp() {
        return new GuessResult.Defeat(userAnswer, attempts, maxAttempts);
    }
}
