package edu.project2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject2 {
    private static Random RANDOM;

    @BeforeAll
    static void setUp() {
        try {
            Class<?> clazz = RecursiveBacktrackingMazeGenerator.class;
            Field field = clazz.getDeclaredField("RANDOM");
            field.setAccessible(true);
            RANDOM = (Random) field.get("RANDOM");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Отрисовка заранее известного лабиринта, вывод совпадает с ожидаемым")
    void renderMazeTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        ConsoleMazeRenderer consoleMazeRenderer = new ConsoleMazeRenderer();

        Maze maze = mazeGenerator.generate(7, 7);
        String expectedMaze =
            """
            ■■■■■■■
            ■   ■ ■
            ■■■ ■ ■
            ■ ■ ■ ■
            ■ ■ ■ ■
            ■     ■
            ■■■■■■■
            """;

        assertEquals(expectedMaze, consoleMazeRenderer.render(maze));
    }

    @Test
    @DisplayName("Нашёл путь из стартовой в конечную")
    void pathExistTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(7, 7);
        List<Coordinate> result = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 1));

        int[][] expectedCoordinates =
            new int[][] {{1, 1}, {1, 2}, {1, 3}, {2, 3}, {3, 3}, {4, 3}, {5, 3}, {5, 2}, {5, 1}, {4, 1}, {3, 1}};

        List<Coordinate> expectedPath = new ArrayList<>();
        for (int i = 0; i < expectedCoordinates.length; i++) {
            expectedPath.add(new Coordinate(expectedCoordinates[i][0], expectedCoordinates[i][1]));
        }

        assertEquals(expectedPath, result);
    }

    @Test
    @DisplayName("Не нашёл путь из стартовой в конечную (стена)")
    void pathNotExistToEndTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(7, 7);
        Throwable thrown = catchThrowable(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(2, 1)));
        assertThat(thrown).hasMessage("Не существует пути, так как конечная клетка стена.");
    }

    @Test
    @DisplayName("Не нашёл путь из стартовой (стена) в конечную")
    void pathNotExistFromStartTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(7, 7);
        Throwable thrown = catchThrowable(() -> solver.solve(maze, new Coordinate(2, 1), new Coordinate(1, 1)));
        assertThat(thrown).hasMessage("Не существует пути, так как стартовая клетка стена.");
    }

    @Test
    @DisplayName("Конечная точка за рамками лабиринта при нахождении пути")
    void pathNotExistToIncorrectEndTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(7, 7);
        Throwable thrown = catchThrowable(() -> solver.solve(maze, new Coordinate(2, 1), new Coordinate(1, 10)));
        assertThat(thrown).hasMessage("Конечная клетка выходит за пределы лабиринта.");
    }

    @Test
    @DisplayName("Стартовая точка за рамками лабиринта при нахождении пути")
    void pathNotExistFromIncorrectStartTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(7, 7);
        Throwable thrown = catchThrowable(() -> solver.solve(maze, new Coordinate(10, 1), new Coordinate(1, 1)));
        assertThat(thrown).hasMessage("Стартовая клетка выходит за пределы лабиринта.");
    }

    @Test
    @DisplayName("Лабиринт меньше чем минимальный")
    void mazeIsLessThanMinTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        Throwable thrown = catchThrowable(() -> mazeGenerator.generate(3, 4));
        assertThat(thrown).hasMessage("Лабиринт может быть минимум размера 7x7");
    }
}
