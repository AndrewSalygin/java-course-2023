package edu.hw10;

import edu.hw10.Task1.RandomObjectGenerator;
import edu.hw10.Task1.annotations.Max;
import edu.hw10.Task1.annotations.Min;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomObjectGeneratorTest {
    @Test
    public void carClassTest() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        Car car = generator.nextObject(Car.class, String.class, String.class, int.class, double.class);
        Assertions.assertThat(car.brand).isNotNull();
        Assertions.assertThat(car.model).isNotNull();
        Assertions.assertThat(car.year).isGreaterThan(2000).isLessThan(2020);
        Assertions.assertThat(car.price).isGreaterThan(0);
    }

    @Test
    public void companyClassTest() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        Company company = generator.nextObject(Company.class, String.class, String.class, int.class, PersonRecord.class);
        Assertions.assertThat(company.name).isNotNull();
        Assertions.assertThat(company.ceo).isNotNull();
        Assertions.assertThat(company.employeeCount).isGreaterThan(0);
        Assertions.assertThat(company.ceoRecord).isNotNull();
        Assertions.assertThat(company.ceoRecord.name).isNotNull();
        Assertions.assertThat(company.ceoRecord.age).isNotNull();
    }

    @Test
    public void mixedTypesClassTest() {
        RandomObjectGenerator generator = new RandomObjectGenerator();
        MixedTypesClass mixedTypesClass = generator.nextObject(MixedTypesClass.class);
        Assertions.assertThat(mixedTypesClass.value).isNotNull();
        Assertions.assertThat(mixedTypesClass.value2).isNotNull();
        Assertions.assertThat(mixedTypesClass.value3).isNotNull();
        Assertions.assertThat(mixedTypesClass.value4).isNotNull();
        Assertions.assertThat(mixedTypesClass.value5).isNotNull();
        Assertions.assertThat(mixedTypesClass.value6).isNotNull();
        Assertions.assertThat(mixedTypesClass.value7).isNotNull();
        Assertions.assertThat(mixedTypesClass.value8).isNotNull();
    }

    public static class Car {
        private final String brand;
        private final String model;
        private final int year;
        private final double price;

        public Car(String brand, String model, @Max(2020) @Min(2000) int year, double price) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.price = price;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public int getYear() {
            return year;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
        }
    }

    public static class Company {
        private final String name;
        private final String ceo;
        private final int employeeCount;
        private final PersonRecord ceoRecord;

        public Company(String name, String ceo, @Min(0) int employeeCount, PersonRecord ceoRecord) {
            this.name = name;
            this.ceo = ceo;
            this.employeeCount = employeeCount;
            this.ceoRecord = ceoRecord;
        }

        public String getName() {
            return name;
        }

        public String getCeo() {
            return ceo;
        }

        public int getEmployeeCount() {
            return employeeCount;
        }

        public PersonRecord getCeoRecord() {
            return ceoRecord;
        }

        @Override
        public String toString() {
            return "Company{" +
                "name='" + name + '\'' +
                ", ceo='" + ceo + '\'' +
                ", employeeCount=" + employeeCount +
                ", ceoRecord=" + ceoRecord +
                '}';
        }
    }

    public record PersonRecord(String name, @Min(10) @Max(90) Integer age) {
    }

    public record MixedTypesClass(
        String value,
        Integer value2,
        Boolean value3,
        float value4,
        Double value5,
        Long value6,
        Byte value7,
        char value8
    ) {
    }

    public class ClassExample {
    }

}
