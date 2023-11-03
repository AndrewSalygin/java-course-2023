package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @ParameterizedTest
    @DisplayName("Тест из примера")
    @MethodSource({"provideDataForExampleTest"})
    void exampleTest(List<?> words, HashMap<?, Integer> expectedMap) {
        assertThat(Task3.freqDist(words)).isEqualTo(expectedMap);
    }
    static Stream<Arguments> provideDataForExampleTest() {
        return Stream.of(
            Arguments.of(List.of("a", "bb", "a", "bb"),  new HashMap<>(Map.of("bb", 2, "a", 2))),
            Arguments.of(List.of("this", "and", "that", "and"), new HashMap<>(Map.of("that", 1, "and", 2,
                "this", 1))),
            Arguments.of(List.of("код", "код", "код", "bug"), new HashMap<>(Map.of("код", 3, "bug", 1))),
            Arguments.of(List.of(1, 1, 2, 2), new HashMap<>(Map.of(1, 2, 2, 2)))
        );
    }

    @Test
    @DisplayName("Тест на разные типы")
    void differentTypesTest() {
        assertThat(Task3.freqDist(List.of(1, 1, "test", "test"))).isEqualTo(new HashMap<>(Map.of(1, 2, "test", 2)));
    }

    @Test
    @DisplayName("Тест на пустой список")
    void emptyListTest() {
        assertThat(Task3.freqDist(List.of())).isEqualTo(new HashMap<>());
    }
}
