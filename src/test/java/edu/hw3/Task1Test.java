package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @ParameterizedTest
    @DisplayName("Латиница")
    @CsvSource({"Hello world!, Svool dliow!",
        "Any fool can write code that a computer can understand. " +
            "Good programmers write code that humans can understand. " +
            "― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z " +
            "xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv " +
            "xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"})
    void latinTest(String inputString, String expectedString) {
        assertThat(Task1.atbashCipher(inputString)).isEqualTo(expectedString);
    }

    @ParameterizedTest
    @DisplayName("Кириллица")
    @CsvSource({"Кошка ест рыбу, Кошка ест рыбу"})
    void cyrillicTest(String inputString, String expectedString) {
        assertThat(Task1.atbashCipher(inputString)).isEqualTo(expectedString);
    }
}
