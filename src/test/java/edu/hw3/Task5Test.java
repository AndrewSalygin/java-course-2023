package edu.hw3;

import edu.hw3.Task5.Contact;
import edu.hw3.Task5.Order;
import edu.hw3.Task5.Task5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class Task5Test {
    @ParameterizedTest
    @DisplayName("Тест из примера")
    @MethodSource({"provideDataForExampleTest"})
    void exampleTest(String[] contactsInput, Order order, List<Contact> contactsExpected) {
        assertIterableEquals(Task5.parseContacts(contactsInput, order), contactsExpected);
    }

    static Stream<Arguments> provideDataForExampleTest() {
        return Stream.of(
            Arguments.of(new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                Order.ASC, List.of(new Contact("Thomas", "Aquinas"),
                    new Contact("Rene", "Descartes"), new Contact("David", "Hume"),
                    new Contact("John", "Locke"))),
            Arguments.of(new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"}, Order.DESC,
                List.of(new Contact("Carl", "Gauss"), new Contact("Leonhard", "Euler"),
                    new Contact("Paul", "Erdos"))),
            Arguments.of(new String[] {}, Order.DESC,
                List.of()),
            Arguments.of(null, Order.DESC,
                List.of())
        );
    }
}
