package edu.project2;

import java.util.ArrayList;
import java.util.List;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public List<Cell> getNeighbors(Cell currentCell) {
        List<Cell> neighbors = new ArrayList<>();

        int x = currentCell.getCol();
        int y = currentCell.getRow();

        if (y > 0) {
            neighbors.add(grid[y - 1][x]);
        }

        if (y < grid.length - 1) {
            neighbors.add(grid[y + 1][x]);
        }

        if (x > 0) {
            neighbors.add(grid[y][x - 1]);
        }

        if (x < grid[0].length - 1) {
            neighbors.add(grid[y][x + 1]);
        }

        return neighbors;
    }
}
