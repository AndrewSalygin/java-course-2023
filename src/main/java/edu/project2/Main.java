package edu.project2;

public class Main {
    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new RecursiveBacktrackingMazeGenerator();
        ConsoleMazeRenderer consoleMazeRenderer = new ConsoleMazeRenderer();
        consoleMazeRenderer.render(mazeGenerator.generate(21, 41));
    }
}
