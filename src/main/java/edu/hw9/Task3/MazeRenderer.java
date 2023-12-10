package edu.hw9.Task3;

import java.util.List;

public interface MazeRenderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
