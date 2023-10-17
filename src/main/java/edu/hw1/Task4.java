package edu.hw1;

public final class Task4 {

    private Task4() {
    }

    public static String fixStringOrder(String s) {
        StringBuilder resultString = new StringBuilder(s.length());
        for (int i = 1; i < s.length(); i += 2) {
            resultString.append(s.charAt(i));
            resultString.append(s.charAt(i - 1));
        }
        if (s.length() % 2 == 1) {
            resultString.append(s.charAt(s.length() - 1));
        }
        return resultString.toString();
    }
}
