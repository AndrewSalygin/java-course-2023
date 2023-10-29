package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.TreeMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Только null в TreeMap")
    void onlyNullInTreeMapTest() {
        TreeMap<String, String> tree = new TreeMap<>(Comparator.nullsLast(
            Comparator.naturalOrder()
        ));
        tree.put(null, "test");
        assertThat(tree.containsKey(null)).isTrue();
    }
    @Test
    @DisplayName("В TreeMap есть другие элементы, кроме null")
    void notOnlyNullInTreeMapTest() {
        TreeMap<String, String> tree = new TreeMap<>(Comparator.nullsLast(
            Comparator.naturalOrder()
        ));
        tree.put("test1", "test1");
        tree.put("test2", "test2");
        tree.put(null, "test3");
        tree.put("test3", "test4");
        tree.put("test4", "test5");
        assertThat(tree.containsKey(null)).isTrue();
    }
}
