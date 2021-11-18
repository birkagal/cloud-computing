package com.birkagal.people.service;

import com.birkagal.people.model.Person;

import java.util.List;

public interface PeopleService {

    Person store(Person newPerson);

    List<Person> getAll(int page, int size);

    void deleteAll();

    List<Person> findPeopleOlderThan(long value, int page, int size);

    List<Person> findAllByNameEquals(String name, int page, int size);

    Person get(String id);

    void update(String id, Person updatedPerson);
}
