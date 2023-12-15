package edu.hw8.Task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MD5CrackConsistent {
    private HashMap<String, String> encryptedPasswords;
    private HashMap<String, String> decryptedPasswords;

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_PASSWORD_LENGTH = 4;

    public MD5CrackConsistent(Map<String, String> encryptedPassword) {
        this.encryptedPasswords = new HashMap<>(encryptedPassword);
        this.decryptedPasswords = new HashMap<>();
    }

    public void crackPasswords() {
        StringBuilder passwordBuilder = new StringBuilder("0000");
        while (decryptedPasswords.size() < encryptedPasswords.size()
            && passwordBuilder.length() != MAX_PASSWORD_LENGTH + 1) {
            String hash = md5(passwordBuilder.toString());
            String user = encryptedPasswords.getOrDefault(hash, "");
            if (!user.isEmpty()) {
                decryptedPasswords.put(user, passwordBuilder.toString());
            }
            nextPassword(passwordBuilder);
        }
    }

    public Map<String, String> getDecryptedPasswords() {
        return Collections.unmodifiableMap(decryptedPasswords);
    }

    private void nextPassword(StringBuilder passwordBuilder) {
        int index = passwordBuilder.length() - 1;

        while (index >= 0) {
            int alphabetIndex = ALPHABET.indexOf(passwordBuilder.charAt(index));

            if (alphabetIndex < ALPHABET.length() - 1) {
                passwordBuilder.setCharAt(index, ALPHABET.charAt(alphabetIndex + 1));
                break;
            } else {
                passwordBuilder.setCharAt(index, ALPHABET.charAt(0));
                index--;
            }
        }

        if (index < 0) {
            passwordBuilder.insert(0, ALPHABET.charAt(0));
        }
    }

    @SuppressWarnings("MagicNumber")
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();

            BigInteger num = new BigInteger(1, digest);
            StringBuilder hash = new StringBuilder(num.toString(16));
            while (hash.length() < 32) {
                hash.insert(0, "0");
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
