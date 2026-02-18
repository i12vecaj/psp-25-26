package com.myapi.datastore;

import com.myapi.model.Person;

import java.util.HashMap;
import java.util.Map;

public class DataWarehouse {
    private final Map<String, Person> people = new HashMap<>();

    public void addPerson(Person p) { people.put(p.getName(), p); }

    public Person getPerson(String name) { return people.get(name); }

    public boolean removePerson(String name) { return people.remove(name) != null; }
}
