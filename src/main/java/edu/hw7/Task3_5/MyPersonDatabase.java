package edu.hw7.Task3_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> personMap = new HashMap<>();
    private final Map<String, List<Integer>> nameIds = new HashMap<>();
    private final Map<String, List<Integer>> addressIds = new HashMap<>();
    private final Map<String, List<Integer>> phoneIds = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personMap.put(person.id(), person);

            addToIdsMap(nameIds, person.name(), person.id());
            addToIdsMap(addressIds, person.address(), person.id());
            addToIdsMap(phoneIds, person.phoneNumber(), person.id());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = personMap.get(id);
            if (person != null) {
                removeFromIdsMap(nameIds, person.name(), id);
                removeFromIdsMap(addressIds, person.address(), id);
                removeFromIdsMap(phoneIds, person.phoneNumber(), id);

                personMap.remove(id);
            } else {
                throw new RuntimeException("Такого человека нет");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return getListByIds(nameIds, name);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return getListByIds(addressIds, address);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return getListByIds(phoneIds, phone);
        } finally {
            lock.readLock().unlock();
        }
    }

    private void addToIdsMap(Map<String, List<Integer>> idsMap, String key, int id) {
        idsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(id);
    }

    private void removeFromIdsMap(Map<String, List<Integer>> idsMap, String key, int id) {
        List<Integer> ids = idsMap.get(key);
        if (ids != null) {
            ids.remove(Integer.valueOf(id));
            if (ids.isEmpty()) {
                idsMap.remove(key);
            }
        } else {
            throw new RuntimeException("Таких людей нет");
        }
    }

    private List<Person> getListByIds(Map<String, List<Integer>> idsMap, String key) {
        List<Integer> ids = idsMap.get(key);
        if (ids != null) {
            List<Person> result = new ArrayList<>();
            for (int id : ids) {
                result.add(personMap.get(id));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
