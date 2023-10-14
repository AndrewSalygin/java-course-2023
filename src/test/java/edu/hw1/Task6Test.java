package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task6Test {
    @ParameterizedTest
    @DisplayName("Число с одинаковыми цифрами")
    @CsvSource({"1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"})
    void numberHasIdenticalDigitsTest(int number) {
        Throwable thrown = catchThrowable(() -> Task6.countK(number));
        assertThat(thrown).hasMessage("Число имеет все одинаковые цифры.");
    }

    @ParameterizedTest
    @DisplayName("Число с тремя одинаковыми цифрами и разница между разными цифрами равна 1 (случай разности равной 999)")
    @CsvSource({"3332", "1110", "6555"})
    void numberHasThreeIdenticalDigitsTest(int number) {
        int result = Task6.countK(number);
        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("Число с тремя цифрами")
    void numberHasThreeDigitsTest() {
        Throwable thrown = catchThrowable(() -> Task6.countK(100));
        assertThat(thrown).hasMessage("Число должно быть больше 1000 и состоять из 4-ех знаков.");
    }

    @Test
    @DisplayName("Число с пятью цифрами")
    void numberHasFiveDigitsTest() {
        Throwable thrown = catchThrowable(() -> Task6.countK(10010));
        assertThat(thrown).hasMessage("Число должно быть больше 1000 и состоять из 4-ех знаков.");
    }

    @ParameterizedTest
    @DisplayName("Четырёхзначное число большее 1000 и меньшее 10000")
    @CsvSource({"6621, 5", "6554, 4", "1234, 3"})
    void fourDigitNumberGreaterThan1000Test(int number, int expectedResult) {
        int result = Task6.countK(number);
        assertThat(result).isEqualTo(expectedResult);
    }
}
