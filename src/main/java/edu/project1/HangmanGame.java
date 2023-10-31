package edu.project1;

public abstract class HangmanGame {
    final static String THE_WORD_CONSTRUCTION = "The word: ";
    DictionaryImplementation dictionary;

    public HangmanGame(String[] words) {
        dictionary = new DictionaryImplementation(words);
    }

    abstract void initGame();

    abstract void run();
}
