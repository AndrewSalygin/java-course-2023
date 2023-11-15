package edu.project2;

import java.util.List;

public class ConsoleMazeRenderer implements MazeRenderer {
    @Override
    public String render(Maze maze) {
        StringBuilder mazeString = new StringBuilder();

        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                mazeString.append(grid[i][j].getType().getSymbol());
            }
            mazeString.append('\n');
        }
        return mazeString.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder mazeString = new StringBuilder();

        Cell[][] grid = maze.getGrid();
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (path.contains(new Coordinate(i, j))) {
                    mazeString.append(Cell.Type.PATH.getSymbol());
                } else {
                    mazeString.append(grid[i][j].getType().getSymbol());
                }
            }
            mazeString.append('\n');
        }
        return mazeString.toString();
    }
}
