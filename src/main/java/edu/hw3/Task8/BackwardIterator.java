package edu.hw3.Task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> elements;
    private int index;

    public BackwardIterator(Collection<T> collection) {
        elements = collection.stream().toList();
        index = collection.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            T item = elements.get(index);
            index--;
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }
}
