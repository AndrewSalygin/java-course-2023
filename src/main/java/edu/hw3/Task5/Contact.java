package edu.hw3.Task5;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Contact implements Comparable<Contact> {
    private String surname;
    private String name;

    public Contact(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Contact(String name) {
        this.surname = "";
        this.name = name;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(surname, contact.surname) && Objects.equals(name, contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name);
    }

    @Override
    public int compareTo(@NotNull Contact contact) {
        if (!this.surname.equals("") && !contact.surname.equals("")) {
            return this.surname.compareTo(contact.surname);
        } else if (this.surname.equals("") && !contact.surname.equals("")) {
            return this.name.compareTo(contact.surname);
        } else if (!this.surname.equals("") && contact.surname.equals("")) {
            return this.surname.compareTo(contact.name);
        } else {
            return this.name.compareTo(contact.name);
        }
    }

    @Override public String toString() {
        if (surname.equals("")) {
            return name;
        }
        return name + ' ' + surname;
    }
}
