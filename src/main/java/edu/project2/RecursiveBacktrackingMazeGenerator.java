package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursiveBacktrackingMazeGenerator implements MazeGenerator {
    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = initGrid(height, width);

        // Указываем начальную клетку
        int startX = 1;
        int startY = 1;
        grid[startX][startY].setType(Cell.Type.PASSAGE);

        generateMazeRecursively(grid, startX, startY);

        return new Maze(height, width, grid);
    }

    private void generateMazeRecursively(Cell[][] grid, int x, int y) {
        List<Direction> directions = new ArrayList<>(List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT));
        Collections.shuffle(directions);

        for (Direction direction : directions) {
            int nextX = x;
            int nextY = y;

            switch (direction) {
                case UP -> nextY += 2;
                case RIGHT -> nextX += 2;
                case DOWN -> nextY -= 2;
                case LEFT -> nextX -= 2;
            }

            if (nextX > 0 && nextX < grid[0].length - 1 && nextY > 0 && nextY < grid.length - 1) {
                if (grid[nextY][nextX].getType() == Cell.Type.WALL) {
                    grid[(y + nextY) / 2][(x + nextX) / 2].setType(Cell.Type.PASSAGE);
                    grid[nextY][nextX].setType(Cell.Type.PASSAGE);
                    generateMazeRecursively(grid, nextX, nextY);
                }
            }
        }
    }

    private Cell[][] initGrid(int height, int width) {
        Cell[][] maze = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }
        return maze;
    }
}
