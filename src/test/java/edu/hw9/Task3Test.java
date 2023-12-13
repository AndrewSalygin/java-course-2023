package edu.hw9;

import edu.hw9.Task3.ConcurrentBacktrackingMazeSolver;
import edu.hw9.Task3.RecursiveBacktrackingMazeGenerator;
import edu.hw9.Task3.Coordinate;
import edu.hw9.Task3.Maze;
import edu.hw9.Task3.MazeGenerator;
import edu.hw9.Task3.MazeSolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
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
    @DisplayName("Нашёл путь из стартовой в конечную")
    void pathExistTest() {
        RANDOM.setSeed(41);
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        MazeSolver solver = new ConcurrentBacktrackingMazeSolver();

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
}
