package com.myapi.repository;

import com.myapi.datastore.DataWarehouse;
import com.myapi.model.Person;

public interface PersonRepository {
    void addPerson(Person p);
    Person getPerson(String name);
    boolean removePerson(String name);
}
