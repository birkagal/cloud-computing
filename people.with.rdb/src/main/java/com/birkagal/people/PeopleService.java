package com.birkagal.people;

public interface PeopleService {

    Person store(Person newPerson);

    Person get(String id);

    Person[] getAll(int page, int size);

    void update(String id, Person updatedPerson);

    void deleteAll();
}
