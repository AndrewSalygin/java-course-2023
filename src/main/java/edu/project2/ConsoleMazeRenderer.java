package edu.project2;

import java.util.List;

public class ConsoleMazeRenderer implements MazeRenderer {
    @Override
    public void render(Maze maze) {
        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                System.out.print(grid[i][j].getType().getSymbol());
            }
            System.out.println();
        }
    }

    @Override
    public void render(Maze maze, List<Coordinate> path) {
//        return null;
    }
}
