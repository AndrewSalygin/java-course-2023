package edu.project2;

import java.util.List;

public interface MazeRenderer {
    void render(Maze maze);
    void render(Maze maze, List<Coordinate> path);
}
