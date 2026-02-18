package com.myapi.repository;

import com.myapi.datastore.DataWarehouse;
import com.myapi.model.Person;

public class InMemoryPersonRepository implements PersonRepository {
    private final DataWarehouse warehouse = new DataWarehouse();

    @Override
    public void addPerson(Person p) { warehouse.addPerson(p); }

    @Override
    public Person getPerson(String name) { return warehouse.getPerson(name); }

    @Override
    public boolean removePerson(String name) { return warehouse.removePerson(name); }
}
