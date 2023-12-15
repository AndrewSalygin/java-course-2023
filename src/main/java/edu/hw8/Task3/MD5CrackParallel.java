package edu.hw8.Task3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MD5CrackParallel {
    private ConcurrentHashMap<String, String> encryptedPasswords;
    private ConcurrentHashMap<String, String> decryptedPasswords;
    private final int countOfThreads;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 5;

    public MD5CrackParallel(Map<String, String> encryptedPassword, int countOfThreads) {
        this.encryptedPasswords = new ConcurrentHashMap<>(encryptedPassword);
        this.decryptedPasswords = new ConcurrentHashMap<>();
        this.countOfThreads = countOfThreads;
    }

    public void crackPasswords() {
        int n = countOfThreads;
        while (n > ALPHABET.length()) {
            n--;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(countOfThreads);
        int partOfWork = ALPHABET.length() / n;

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int count = MIN_PASSWORD_LENGTH - 1; count <= MAX_PASSWORD_LENGTH - 1; count++) {
            String zeros = "0".repeat(count);
            for (int i = 0; i < n; i++) {
                int finalI = i;
                if (i != n - 1) {
                    tasks.add(() -> {
                        crackPasswordsPart(
                            new StringBuilder(ALPHABET.charAt(finalI * partOfWork) + zeros),
                            new StringBuilder(ALPHABET.charAt((finalI + 1) * partOfWork) + zeros)
                        );
                        return null;
                    });
                } else {
                    tasks.add(() -> {
                        crackPasswordsPart(
                            new StringBuilder(ALPHABET.charAt(finalI * partOfWork) + zeros),
                            new StringBuilder("00" + zeros)
                        );
                        return null;
                    });
                }
            }
        }


        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    public void crackPasswordsPart(StringBuilder from, StringBuilder to) {
        while (decryptedPasswords.size() < encryptedPasswords.size() && !(from.compareTo(to) == 0)) {
            String hash = md5(from.toString());
            String user = encryptedPasswords.getOrDefault(hash, "");
            if (!user.isEmpty()) {
                decryptedPasswords.put(user, from.toString());
            }
            nextPassword(from);
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
