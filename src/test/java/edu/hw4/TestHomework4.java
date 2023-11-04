package edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHomework4 {
    private final Animal MURKA =
        new Animal("Мурка", Animal.Type.CAT, Animal.Sex.F, 2, 70, 3000, false);
    private final Animal JACK =
        new Animal("Джек", Animal.Type.DOG, Animal.Sex.M, 3, 120, 5000, true);
    private final Animal SPARROW =
        new Animal("Воробушек", Animal.Type.BIRD, Animal.Sex.M, 1, 20, 500, false);
    private final Animal PIKE =
        new Animal("Щучка", Animal.Type.FISH, Animal.Sex.F, 2, 130, 1000, true);
    private final Animal SPIDER =
        new Animal("Павучок Павучкович Павучков", Animal.Type.SPIDER, Animal.Sex.M, 1, 5, 10, false);
    private final Animal VASYA =
        new Animal("Вася", Animal.Type.CAT, Animal.Sex.M, 4, 80, 4000, true);
    private final Animal SHARIK =
        new Animal("Шарик Львович Каратун", Animal.Type.DOG, Animal.Sex.M, 7, 140, 6000, false);

    Homework4 homework4;

    @BeforeEach
    public void setUp() {
        homework4 = new Homework4(List.of(MURKA, JACK, SPARROW, PIKE, SPIDER, VASYA, SHARIK));
    }

    @Test
    @DisplayName("Тест 1 задания")
    void task1Test() {
        List<Animal> expectedResult = new ArrayList<>(List.of(SPIDER, SPARROW, MURKA, VASYA, JACK, PIKE, SHARIK));
        List<Animal> result = homework4.task1();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 2 задания")
    void task2Test() {
        int k = 3;
        List<Animal> expectedResult = new ArrayList<>(List.of(SHARIK, JACK, VASYA));
        List<Animal> result = homework4.task2(k);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 3 задания")
    void task3Test() {
        Map<Animal.Type, Integer> expectedResult = new HashMap<>(Map.of(
            Animal.Type.CAT, 2,
            Animal.Type.DOG, 2,
            Animal.Type.BIRD, 1,
            Animal.Type.FISH, 1,
            Animal.Type.SPIDER, 1
        ));
        Map<Animal.Type, Integer> result = homework4.task3();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 4 задания")
    void task4Test() {
        Animal result = homework4.task4();
        assertEquals(SPIDER, result);
    }

    @Test
    @DisplayName("Тест 5 задания")
    void task5Test() {
        Animal.Sex expectedResult = Animal.Sex.M;
        Animal.Sex result = homework4.task5();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 6 задания")
    void task6Test() {
        Map<Animal.Type, Animal> expectedResult = new HashMap<>(Map.of(
            Animal.Type.CAT, VASYA,
            Animal.Type.DOG, SHARIK,
            Animal.Type.BIRD, SPARROW,
            Animal.Type.FISH, PIKE,
            Animal.Type.SPIDER, SPIDER
        ));
        Map<Animal.Type, Animal> result = homework4.task6();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 7 задания")
    void task7Test() {
        Animal result = homework4.task7(3);
        assertEquals(JACK, result);
    }

    @Test
    @DisplayName("Тест 8 задания")
    void task8Test() {
        int k = 40;
        Animal expectedResult = SPARROW;
        Animal result = homework4.task8(k).get();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 9 задания")
    void task9Test() {
        int expectedResult = 26;
        int result = homework4.task9();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 10 задания")
    void task10Test() {
        List<Animal> expectedResult = new ArrayList<>(List.of(MURKA, JACK, SPARROW, PIKE, SPIDER, SHARIK));
        List<Animal> result = homework4.task10();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 11 задания")
    void task11Test() {
        List<Animal> expectedResult = new ArrayList<>(List.of(JACK, PIKE));
        List<Animal> result = homework4.task11();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 12 задания")
    void task12Test() {
        int expectedResult = 7;
        int result = homework4.task12();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 13 задания")
    void task13Test() {
        List<Animal> expectedResult = new ArrayList<>(List.of(SPIDER, SHARIK));
        List<Animal> result = homework4.task13();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 14 задания")
    void task14Test() {
        int k = 130;
        Boolean expectedResult = true;
        Boolean result = homework4.task14(k);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 15 задания")
    void task15Test() {
        int k = 2;
        int l = 4;
        Map<Animal.Type, Integer> expectedResult = new HashMap<>(Map.of(
            Animal.Type.CAT, 7000,
            Animal.Type.DOG, 5000,
            Animal.Type.FISH, 1000
        ));
        Map<Animal.Type, Integer> result = homework4.task15(k, l);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 16 задания")
    void task16Test() {
        List<Animal> expectedResult = new ArrayList<>(List.of(VASYA, MURKA, JACK, SHARIK, SPARROW, PIKE, SPIDER));
        List<Animal> result = homework4.task16();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 17 задания")
    void task17Test() {
        Boolean expectedResult = false;
        Boolean result = homework4.task17();
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест 18 задания")
    void task18Test() {
        Animal shark =
            new Animal("Акулка", Animal.Type.FISH, Animal.Sex.F, 3, 170, 3000, true);

        Animal dolphin =
            new Animal("Дельфинчик", Animal.Type.FISH, Animal.Sex.M, 1, 80, 2000, true);

        List<Animal> animals1 = new ArrayList<>(List.of(shark));
        List<Animal> animals2 = new ArrayList<>(List.of(dolphin));

        Animal result = homework4.task18(homework4.getAnimals(), animals1, animals2);
        assertEquals(shark, result);
    }
}
