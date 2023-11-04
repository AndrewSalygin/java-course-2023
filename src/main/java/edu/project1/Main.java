package edu.project1;

public final class Main {
    private Main() {}

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        HangmanGame hangmanGame = new HangmanGame(new String[]{"melon"}, new ConsoleInteractionWithGame(), 5);
        hangmanGame.run();
    }
}
