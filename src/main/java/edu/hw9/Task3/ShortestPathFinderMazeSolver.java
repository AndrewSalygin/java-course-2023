package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ShortestPathFinderMazeSolver implements MazeSolver {

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

        Queue<Cell> queue = new LinkedList<>();
        Map<Cell, Cell> parentMap = new HashMap<>();
        boolean pathFound = bfs(maze, start, end, queue, parentMap);

        if (pathFound) {
            return constructPath(end, parentMap);
        } else {
            return Collections.emptyList();
        }
    }

    private boolean bfs(Maze maze, Coordinate start, Coordinate end, Queue<Cell> queue, Map<Cell, Cell> parentMap) {
        Cell startCell = new Cell(start.row(), start.col(), Cell.Type.PASSAGE);
        Cell endCell = new Cell(end.row(), end.col(), Cell.Type.PASSAGE);

        queue.offer(startCell);
        parentMap.put(startCell, null);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            if (currentCell.equals(endCell)) {
                return true;
            }

            List<Cell> neighbors = maze.getNeighbors(currentCell);

            for (Cell neighbor : neighbors) {
                if (neighbor.getType() == Cell.Type.PASSAGE && !parentMap.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    parentMap.put(neighbor, currentCell);
                }
            }
        }

        return false;
    }

    private List<Coordinate> constructPath(Coordinate end, Map<Cell, Cell> parentMap) {
        List<Coordinate> path = new ArrayList<>();
        Cell currentCell = new Cell(end.row(), end.col(), Cell.Type.PASSAGE);

        // Проходимся в обратном порядке
        while (currentCell != null) {
            path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));
            currentCell = parentMap.get(currentCell);
        }

        Collections.reverse(path);
        return path;
    }
}
