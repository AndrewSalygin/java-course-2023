package edu.hw3.Task5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Task5 {
    private static final Comparator<Contact> ASC = Comparator
        .comparing(Contact::getSurname)
        .thenComparing(Contact::getName);

    private static final Comparator<Contact> DESC = ASC.reversed();

    private Task5() {}

    public static List<Contact> parseContacts(String[] stringContacts, Order order) {
        String name;
        String surname;
        String[] info;
        Contact tmpContact;
        List<Contact> contacts = new ArrayList<>();
        if (stringContacts == null) {
            return new ArrayList<>();
        }
        for (String contact : stringContacts) {
            info = contact.split(" ");
            if (info.length == 1) {
                name = info[0];
                tmpContact = new Contact(name);
            } else if (info.length == 2) {
                name = info[0];
                surname = info[1];
                tmpContact = new Contact(name, surname);
            } else {
                throw new IllegalArgumentException("Неправильный формат данных в строке: " + contact);
            }
            contacts.add(tmpContact);
        }

        if (order == Order.ASC) {
            contacts.sort(ASC);
        } else {
            contacts.sort(DESC);
        }
        return contacts;
    }
}
