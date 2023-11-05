package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RecursiveBacktrackingMazeGenerator implements MazeGenerator {
    private static final Random RANDOM = new Random();

    @Override
    @SuppressWarnings("MagicNumber")
    public Maze generate(int height, int width) {
        if (height < 7 || width < 7) {
            throw new RuntimeException("Лабиринт может быть минимум размера 7x7");
        }

        Cell[][] grid = initGrid(height, width);

        // Указываем начальную клетку
        int startX = 1;
        int startY = 1;
        grid[startY][startX].setType(Cell.Type.PASSAGE);

        generateMazeRecursively(grid, startX, startY);

        if (height % 2 == 0) {
            for (int i = 1; i < width - 1; i++) {
                grid[height - 2][i].setType(Cell.Type.PASSAGE);
            }
        }
        if (width % 2 == 0) {
            for (int j = 1; j < height - 1; j++) {
                grid[j][width - 2].setType(Cell.Type.PASSAGE);
            }
        }

        return new Maze(height, width, grid);
    }

    @SuppressWarnings({"MissingSwitchDefault", "InnerAssignment"})
    private void generateMazeRecursively(Cell[][] grid, int x, int y) {
        List<Direction> directions =
            new ArrayList<>(List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT));
        Collections.shuffle(directions, RANDOM);

        int nextX;
        int nextY;
        for (Direction direction : directions) {
            nextX = x;
            nextY = y;

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
