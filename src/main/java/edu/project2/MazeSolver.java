package edu.project2;

import java.util.List;

public interface MazeSolver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
