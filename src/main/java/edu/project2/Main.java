package edu.project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        ConsoleMazeRenderer consoleMazeRenderer = new ConsoleMazeRenderer();
        MazeSolver solver = new RecursiveBacktrackingMazeSolver();

        Maze maze = mazeGenerator.generate(21, 41);
        System.out.println(consoleMazeRenderer.render(maze));

        List<Coordinate> solve = new ArrayList<>();
        try {
            solve = solver.solve(maze, new Coordinate(1, 1), new Coordinate(19, 39));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        System.out.println(consoleMazeRenderer.render(maze, solve));
    }
}
