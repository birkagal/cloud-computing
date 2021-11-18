package com.birkagal.firstmongoservice.people;

import java.util.List;

public interface PeopleService {

    public Person store(Person newPerson);

    public List<Person> findPeopleOlderThan(long value, int size, int page);

    public List<Person> findAllByNameEquals(String name, int size, int page);

    public void deleteAll();

    public List<Person> getAllPeople(int size, int page);

}
