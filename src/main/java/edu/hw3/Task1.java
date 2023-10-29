package edu.hw3;

public final class Task1 {
    private Task1() {}

    public static String atbashCipher(String inputString) {
        char[] encryptedString = new char[inputString.length()];

        char ch;
        for (int i = 0; i < inputString.length(); i++) {
            ch = inputString.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                encryptedString[i] = (char) ('Z' - (ch - 'A'));
            } else if (ch >= 'a' && ch <= 'z') {
                encryptedString[i] = (char) ('z' - (ch - 'a'));
            } else {
                encryptedString[i] = ch;
            }
        }
        return String.valueOf(encryptedString);
    }
}
