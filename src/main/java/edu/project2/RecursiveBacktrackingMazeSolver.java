package edu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursiveBacktrackingMazeSolver implements MazeSolver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (!(start.col() > 0 && start.col() < maze.getGrid()[0].length - 1 && start.row() > 0
            && start.row() < maze.getGrid().length - 1)) {
            throw new RuntimeException("Стартовая клетка выходит за пределы лабиринта.");
        } else if (!(end.col() > 0 && end.col() < maze.getGrid()[0].length - 1 && end.row() > 0
            && end.row() < maze.getGrid().length - 1)) {
            throw new RuntimeException("Конечная клетка выходит за пределы лабиринта.");
        } else if (maze.getGrid()[start.row()][start.col()].getType() == Cell.Type.WALL) {
            throw new RuntimeException("Не существует пути, так как стартовая клетка стена.");
        } else if (maze.getGrid()[end.row()][end.col()].getType() == Cell.Type.WALL) {
            throw new RuntimeException("Не существует пути, так как конечная клетка стена.");
        }


        Cell startCell = new Cell(start.row(), start.col(), Cell.Type.PASSAGE);
        Cell endCell = new Cell(end.row(), end.col(), Cell.Type.PASSAGE);

        List<Coordinate> path = new ArrayList<>();
        boolean pathFound = generatePathRecursively(maze, startCell, endCell, path);

        if (pathFound) {
            return path;
        } else {
            return Collections.emptyList();
        }
    }

    private boolean generatePathRecursively(Maze maze, Cell currentCell, Cell endCell, List<Coordinate> path) {
        if (currentCell.equals(endCell)) {
            path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));
            return true;
        }

        List<Cell> neighbors = maze.getNeighbors(currentCell);

        for (Cell neighbor : neighbors) {
            if (neighbor.getType() == Cell.Type.PASSAGE
                && !path.contains(new Coordinate(neighbor.getRow(), neighbor.getCol()))) {
                path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));

                if (generatePathRecursively(maze, neighbor, endCell, path)) {
                    return true;
                }

                path.remove(new Coordinate(currentCell.getRow(), currentCell.getCol()));
            }
        }

        return false;
    }
}
