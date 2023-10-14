package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task3Test {
    @ParameterizedTest
    @DisplayName("Пустые массивы")
    @MethodSource("provideArraysForEmptyArrays")
    void bothArraysAreEmptyTest(int[] innerArray, int[] outerArray) {
        Throwable thrown = catchThrowable(() -> Task3.isNested(innerArray, outerArray));
        assertThat(thrown).hasMessage("Оба массива не должны быть пустыми.");
    }

    static Stream<Arguments> provideArraysForEmptyArrays() {
        return Stream.of(
            Arguments.of(new int[] {}, new int[] {}),
            Arguments.of(new int[] {}, null),
            Arguments.of(null, new int[] {}),
            Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @DisplayName("Массив вложен")
    @MethodSource("provideArraysForNestedArray")
    void arrayIsNestedTest(int[] innerArray, int[] outerArray) {
        boolean result = Task3.isNested(innerArray, outerArray);
        assertThat(result).isTrue();
    }

    static Stream<Arguments> provideArraysForNestedArray() {
        return Stream.of(
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {0, 6}),
            Arguments.of(new int[] {3, 1}, new int[] {4, 0})
        );
    }

    @ParameterizedTest
    @DisplayName("Массив не вложен")
    @MethodSource("provideArraysForNotNestedArray")
    void arrayIsNotNestedTest(int[] innerArray, int[] outerArray) {
        boolean result = Task3.isNested(innerArray, outerArray);
        assertThat(result).isFalse();
    }

    static Stream<Arguments> provideArraysForNotNestedArray() {
        return Stream.of(
            Arguments.of(new int[] {9, 9, 8}, new int[] {8, 9}),
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {2, 3})
        );
    }
}
