package edu.hw7;

import edu.hw7.Task3.MyPersonDatabase;
import edu.hw7.Task3.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3_5Test {
    MyPersonDatabase database;

    @BeforeEach
    void setUp() {
        database = new MyPersonDatabase();
    }

    @Test
    @DisplayName("Метод add")
    void addTest() {
        Person person = new Person(1, "Виталий", "пр. Победы, дом 5", "+72342115835");
        database.add(person);
        assertEquals(database.findByName("Виталий"), List.of(person));
    }

    @Test
    @DisplayName("Метод delete")
    void deleteTest() {
        Person person = new Person(1, "Виталий", "пр. Победы, дом 5", "+72342115835");
        database.add(person);
        database.delete(1);
        assertEquals(database.findByPhone("+72342115835"), List.of());
    }

    @Test
    @DisplayName("Метод delete не найден человек")
    void wrongDeleteTest() {
        Person person = new Person(1, "Виталий", "пр. Победы, дом 5", "+72342115835");
        database.add(person);
        Throwable thrown = catchThrowable(() -> database.delete(2));
        assertThat(thrown).hasMessage("Такого человека нет");
    }

    @Test
    @DisplayName("Метод findByAddress")
    void findByAddressTest() {
        Person person1 = new Person(1, "Виталий", "пр. Победы, дом 5", "+72342115835");
        Person person2 = new Person(2, "Екатерина", "пр. Победы, дом 5", "+72342115845");
        database.add(person1);
        database.add(person2);
        assertEquals(database.findByAddress("пр. Победы, дом 5"), List.of(person1, person2));
    }
}
