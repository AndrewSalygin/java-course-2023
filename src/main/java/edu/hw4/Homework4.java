package edu.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Homework4 {
    private final List<Animal> animals;

    public Homework4(List<Animal> animals) {
        this.animals = new ArrayList<>(animals);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Animal> task1() {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    public List<Animal> task2(int k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    public Map<Animal.Type, Integer> task3() {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(animal -> 1)));
    }

    public Animal task4() {
        return animals.stream()
            .max(Comparator.comparing(animal -> animal.name().length()))
            .orElse(null);
    }

    public Animal.Sex task5() {
        Map<Animal.Sex, Long> sexCountMap = animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));

        long maleCount = sexCountMap.getOrDefault(Animal.Sex.M, 0L);
        long femaleCount = sexCountMap.getOrDefault(Animal.Sex.F, 0L);

        return maleCount > femaleCount ? Animal.Sex.M : Animal.Sex.F;
    }

    public Map<Animal.Type, Animal> task6() {
        return animals.stream()
            .collect(Collectors.toMap(
                Animal::type,
                animal -> animal,
                (existingAnimal, newAnimal) -> existingAnimal.weight() > newAnimal.weight() ? existingAnimal : newAnimal
            ));
    }

    public Animal task7(int k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst().orElseGet(null);
    }

    public Optional<Animal> task8(int k) {
        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public int task9() {
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public List<Animal> task10() {
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    @SuppressWarnings("MagicNumber")
    public List<Animal> task11() {
        return animals.stream()
            .filter(animal -> animal.bites() && animal.height() > 100)
            .collect(Collectors.toList());
    }

    public Integer task12() {
        long count = animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();

        return (int) count;
    }

    public List<Animal> task13() {
        return animals.stream()
            .filter(animal -> animal.name().split(" ").length > 2)
            .collect(Collectors.toList());
    }

    public Boolean task14(int k) {
        return animals.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    public Map<Animal.Type, Integer> task15(int k, int l) {
        return animals.stream()
            .filter(animal -> animal.age() >= k && animal.age() <= l)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    public List<Animal> task16() {
        return animals.stream()
            .sorted(Comparator
                .comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());
    }

    public boolean task17() {
        long spiderBites = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .filter(Animal::bites)
            .count();

        long dogBites = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .filter(Animal::bites)
            .count();

        return spiderBites > dogBites;
    }

    @SafeVarargs public final Animal task18(List<Animal> animals1, List<Animal> animals2, List<Animal>... animals) {
        Stream<List<Animal>> allAnimalStreams = Stream.concat(Stream.of(animals1, animals2), Arrays.stream(animals));

        List<Animal> allAnimals = allAnimalStreams
            .flatMap(List::stream)
            .toList();

        Optional<Animal> heaviestFish = allAnimals.stream()
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight));

        return heaviestFish.orElse(null);
    }

}
