package edu.hw3;

import edu.hw3.Task8.BackwardIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    static List<Integer> list;
    @BeforeEach void setUp() {
        list = new ArrayList<>();
    }
    @Test
    @DisplayName("Тест из примера")
    void exampleTest() {
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> reverseIterator = new BackwardIterator<>(list);

        int i = 3;
        while (reverseIterator.hasNext()) {
            int item = reverseIterator.next();
            assertThat(item).isEqualTo(i--);
        }
    }

    @Test
    @DisplayName("Пустой список")
    void emptyListTest() {
        Iterator<Integer> reverseIterator = new BackwardIterator<>(list);

        assertThat(reverseIterator.hasNext()).isFalse();
    }
}
