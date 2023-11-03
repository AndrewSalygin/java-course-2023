package edu.project1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private final int maxAttempts;
    private String answer;
    private int attempt;
    private char[] userAnswer;
    private HashSet<Character> triedLetters;

    public Board(String answer, int maxAttempts) {
        this.answer = answer;
        userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, '*');
        attempt = 0;
        this.maxAttempts = maxAttempts;
        triedLetters = new HashSet<>();
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttempt() {
        return attempt;
    }

    public char[] getUserAnswer() {
        return userAnswer;
    }

    public HashSet<Character> getTriedLetters() {
        return triedLetters;
    }

    public GameStatus guess(char guess) {
        if (answer.indexOf(guess) == -1) {
            if (attempt + 1 == maxAttempts) {
                attempt++;
                return GameStatus.DEFEAT;
            }
            attempt++;
            return GameStatus.FAILED_GUESS;
        }
        HashSet<Integer> indexes = (HashSet<Integer>) IntStream.range(0, answer.length())
            .filter(i -> answer.charAt(i) == guess)
            .boxed().collect(Collectors.toSet());
        for (int index : indexes) {
            userAnswer[index] = guess;
        }

        if (Arrays.equals(userAnswer, answer.toCharArray())) {
            return GameStatus.WIN;
        }
        return GameStatus.SUCCESS_GUESS;
    }
}
