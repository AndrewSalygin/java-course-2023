package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class Task8Test {
    @ParameterizedTest
    @DisplayName("Переданный массив большей размерности")
    @MethodSource("provideArrayLarger8x8")
    void passedArrayWithGreaterDimensionTest(int[][] board) {
        Throwable thrown = catchThrowable(() -> Task8.knightBoardCapture(board));
        assertThat(thrown).hasMessage("Размер доски должен быть 8x8.");
    }

    static Stream<Arguments> provideArrayLarger8x8() {
        return Stream.of(
            Arguments.of((Object) new int[9][9]),
            Arguments.of((Object) new int[8][9]),
            Arguments.of((Object) new int[9][8])
        );
    }

    @ParameterizedTest
    @DisplayName("Переданный массив меньшей размерности")
    @MethodSource("provideArraySmaller8x8")
    void passedArrayWithLesserDimensionTest(int[][] board) {
        Throwable thrown = catchThrowable(() -> Task8.knightBoardCapture(board));
        assertThat(thrown).hasMessage("Размер доски должен быть 8x8.");
    }

    static Stream<Arguments> provideArraySmaller8x8() {
        return Stream.of(
            Arguments.of((Object) new int[7][7]),
            Arguments.of((Object) new int[8][7]),
            Arguments.of((Object) new int[7][8])
        );
    }

    @Test
    @DisplayName("Кони не захватывают друг друга")
    void knightsDoNotCaptureEachOther() {
        boolean result = Task8.knightBoardCapture(new int[][] {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        });
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Хотя бы один конь захватил другого")
    @MethodSource("provideArrayForKnightsCaptureEachOther")
    void knightsCaptureEachOtherTest(int[][] board) {
        boolean result = Task8.knightBoardCapture(board);
        assertThat(result).isFalse();
    }

    static Stream<Arguments> provideArrayForKnightsCaptureEachOther() {
        return Stream.of(
            Arguments.of((Object) new int[][] {
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1}
            }),
            Arguments.of((Object) new int[][] {
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
            })
        );
    }

    @Test
    @DisplayName("Кони стоят в центре")
    void knightsAreInTheCenterTest() {
        boolean result = Task8.knightBoardCapture(new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        });
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Кони стоят на краях доски")
    void knightsAreAtTheBoardsEdgesTest() {
        boolean result = Task8.knightBoardCapture(new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        });
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("В массиве не только значения 1 или 0")
    void boardHasNotOnlyZeroOrOneTest() {
        Throwable thrown = catchThrowable(() -> Task8.knightBoardCapture(new int[][] {
            {1, 1, 1, 1, 1, 1, 999, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        }));
        assertThat(thrown).hasMessage("В массиве должны быть 1 или 0.");
    }
}
