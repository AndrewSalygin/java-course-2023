package edu.hw8;

import edu.hw8.Task3.MD5CrackConsistent;
import edu.hw8.Task3.MD5CrackParallel;
import edu.hw8.Task3.PasswordEncrypter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {

    @Test
    @DisplayName("Однопоточная версия MD5Crack")
    public void md5CrackConsistentTest() {
        HashMap<String, String> inputData = new HashMap<>(Map.of(
            PasswordEncrypter.md5("pass"), "user1",
            PasswordEncrypter.md5("quit"), "user2"
        ));

        TreeMap<String, String> expectedResult = new TreeMap<>(Map.of(
            "user1", "pass",
            "user2", "quit"
        ));
        MD5CrackConsistent md5Crack = new MD5CrackConsistent(inputData);
        md5Crack.crackPasswords();
        TreeMap<String, String> decodedUserData = new TreeMap<>(md5Crack.getDecryptedPasswords());

        assertEquals(expectedResult, decodedUserData);
    }

    @Test
    @DisplayName("Многопоточная версия MD5Crack")
    public void md5CrackParallelTest() {
        HashMap<String, String> inputData = new HashMap<>(Map.of(
            PasswordEncrypter.md5("pass"), "user1",
            PasswordEncrypter.md5("quit"), "user2",
            PasswordEncrypter.md5("pswrd"), "user3"
        ));

        TreeMap<String, String> expectedResult = new TreeMap<>(Map.of(
            "user1", "pass",
            "user2", "quit",
            "user3", "pswrd"
        ));


        MD5CrackParallel md5Crack = new MD5CrackParallel(inputData, 4);
        md5Crack.crackPasswords();

        assertEquals(expectedResult, md5Crack.getDecryptedPasswords());
    }
}
