package edu.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class DictionaryImplementation implements Dictionary {
    private static final Random RANDOM = new Random();
    private final List<String> words;
    private boolean isErroneousWords;

    public DictionaryImplementation(String[] inputWords) {
        isErroneousWords = false;
        this.words = getCorrectWords(inputWords);
    }

    public boolean isErroneousWords() {
        return isErroneousWords;
    }

    @Override
    public @NotNull String randomWord() {
        if (words.size() == 0) {
            throw new NoSuchElementException("The dictionary is empty.");
        }
        return words.get(RANDOM.nextInt(words.size()));
    }

    private List<String> getCorrectWords(String[] inputWords) {
        List<String> tmpList = new ArrayList<>();
        boolean isRightWord = true;
        for (String word : inputWords) {
            if (word.length() > 1) {
                for (char ch : word.toCharArray()) {
                    if (!Character.isLetter(ch) || !(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z')) {
                        isRightWord = false;
                        isErroneousWords = true;
                        break;
                    }
                }
                if (isRightWord) {
                    tmpList.add(word.toLowerCase());
                }
                isRightWord = true;
            } else {
                isErroneousWords = true;
            }
        }
        return tmpList;
    }
}
