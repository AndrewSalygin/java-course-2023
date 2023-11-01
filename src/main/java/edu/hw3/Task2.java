package edu.hw3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Task2 {
    private Task2() {}

    public static List<String> clusterize(String inputString) {
        LinkedList<Character> chars = new LinkedList<>();
        StringBuilder resultString = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : inputString.toCharArray()) {
            if (ch == '(') {
                chars.addFirst('(');
                resultString.append('(');
            } else if (ch == ')') {
                if (chars.size() == 0) {
                    throw new IllegalArgumentException("Входная строка имеет неправильный формат.");
                }
                chars.removeFirst();
                resultString.append(')');
                if (chars.size() == 0) {
                    result.add(resultString.toString());
                    resultString.setLength(0);
                }
            } else {
                throw new IllegalArgumentException("Входная строка имеет символы отличные от круглых скобок.");
            }
        }
        return result;
    }
}
