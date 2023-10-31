package edu.project1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static edu.project1.Board.MAX_ATTEMPTS;
import static edu.project1.Board.answer;
import static edu.project1.Board.attempt;

public class ConsolePlayer implements Player {
    private char[] userAnswer;
    HashSet<Character> triedLetters;

    public ConsolePlayer(int lengthAnswer) {
        triedLetters = new HashSet<>();
        userAnswer = new char[lengthAnswer];
        Arrays.fill(userAnswer, '*');
    }

    public char[] getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(char[] userAnswer) {
        this.userAnswer = userAnswer;
    }

    public HashSet<Character> getTriedLetters() {
        return triedLetters;
    }

    public void setTriedLetters(HashSet<Character> triedLetters) {
        this.triedLetters = triedLetters;
    }

    @Override
    public GameStatus guess(char guess) {
        if (answer.indexOf(guess) == -1) {
            if (attempt + 1 == MAX_ATTEMPTS) {
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

    @Override
    public GameStatus giveUp() {
        return GameStatus.DEFEAT;
    }
}
