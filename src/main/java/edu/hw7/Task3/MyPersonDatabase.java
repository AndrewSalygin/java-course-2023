package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> personMap = new HashMap<>();
    private final Map<String, List<Integer>> nameIds = new HashMap<>();
    private final Map<String, List<Integer>> addressIds = new HashMap<>();
    private final Map<String, List<Integer>> phoneIds = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        personMap.put(person.id(), person);

        addToIdsMap(nameIds, person.name(), person.id());
        addToIdsMap(addressIds, person.address(), person.id());
        addToIdsMap(phoneIds, person.phoneNumber(), person.id());
    }

    @Override
    public synchronized void delete(int id) {
        Person person = personMap.get(id);
        if (person != null) {
            removeFromIdsMap(nameIds, person.name(), id);
            removeFromIdsMap(addressIds, person.address(), id);
            removeFromIdsMap(phoneIds, person.phoneNumber(), id);

            personMap.remove(id);
        } else {
            throw new RuntimeException("Такого человека нет");
        }
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return getListByIds(nameIds, name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return getListByIds(addressIds, address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return getListByIds(phoneIds, phone);
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
