package edu.project1;

public interface Player {
    GameStatus guess(char guess);

    GameStatus giveUp();
}
