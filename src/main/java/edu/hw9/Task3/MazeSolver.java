package edu.hw9.Task3;

import java.util.List;

public interface MazeSolver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
