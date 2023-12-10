package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ConcurrentBacktrackingMazeSolver implements MazeSolver {
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
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        boolean pathFound = forkJoinPool.invoke(new PathFindingTask(maze, startCell, endCell, path));
        forkJoinPool.shutdown();

        if (pathFound) {
            return path;
        } else {
            return Collections.emptyList();
        }
    }

    private static class PathFindingTask extends RecursiveTask<Boolean> {
        private final Maze maze;
        private final Cell currentCell;
        private final Cell endCell;
        private final List<Coordinate> path;

        PathFindingTask(Maze maze, Cell currentCell, Cell endCell, List<Coordinate> path) {
            this.maze = maze;
            this.currentCell = currentCell;
            this.endCell = endCell;
            this.path = path;
        }

        @Override
        protected Boolean compute() {
            if (currentCell.equals(endCell)) {
                path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));
                return true;
            }

            List<Cell> neighbors = maze.getNeighbors(currentCell);

            for (Cell neighbor : neighbors) {
                if (neighbor.getType() == Cell.Type.PASSAGE
                    && !path.contains(new Coordinate(neighbor.getRow(), neighbor.getCol()))) {
                    path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));

                    PathFindingTask subTask = new PathFindingTask(maze, neighbor, endCell, path);
                    if (subTask.fork().join()) {
                        return true;
                    }

                    path.remove(new Coordinate(currentCell.getRow(), currentCell.getCol()));
                }
            }

            return false;
        }
    }
}
