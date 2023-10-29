package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task2Test {
    @ParameterizedTest
    @DisplayName("Тест из примера")
    @MethodSource({"provideDataForExampleTest"})
    void exampleTest(String inputString, List<?> expectedList) {
        assertThat(Task2.clusterize(inputString)).isEqualTo(expectedList);
    }
    static Stream<Arguments> provideDataForExampleTest() {
        return Stream.of(
            Arguments.of("()()()", List.of("()", "()", "()")),
            Arguments.of("((()))", List.of("((()))")),
            Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
            Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
        );
    }

    @Test
    @DisplayName("Неправильная (другие символы)")
    void wrongSymbolsInInputTest() {
        Throwable thrown = catchThrowable(() -> Task2.clusterize("(test)"));
        assertThat(thrown).hasMessage("Входная строка имеет символы отличные от круглых скобок.");
    }

    @Test
    @DisplayName("Неправильная (порядок)")
    void wrongOrderTest() {
        Throwable thrown = catchThrowable(() -> Task2.clusterize("))("));
        assertThat(thrown).hasMessage("Входная строка имеет неправильный формат.");
    }

    @Test
    @DisplayName("Пустая")
    void emptySequenceTest() {
        assertThat(Task2.clusterize("")).isEqualTo(new ArrayList<>());
    }
}
