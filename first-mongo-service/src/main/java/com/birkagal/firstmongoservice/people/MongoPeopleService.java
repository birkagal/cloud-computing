package com.birkagal.firstmongoservice.people;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoPeopleService implements PeopleService {
    private PeopleDao people;

    @Autowired
    public MongoPeopleService(PeopleDao people) {
        super();
        this.people = people;
    }

    @Override
    public Person store(Person newPerson) {
        newPerson.setId(null);

        PersonEntity entity = this.toEntity(newPerson);
        entity = this.people
                .save(entity);

        return this.toBoundary(entity);
    }

    @Override
    public List<Person> findPeopleOlderThan(long value, int size, int page) {
        return this.people
                .findAllByBirthdateEpocLessThan(value, PageRequest.of(page, size, Direction.ASC, "birthdateEpoc"))
                .stream()
                .map(this::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findAllByNameEquals(String name, int size, int page) {
        return this.people
                .findAllByNameEquals(name, PageRequest.of(page, size, Direction.ASC, "name"))
                .stream()
                .map(this::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        this.people.deleteAll();
    }

    @Override
    public List<Person> getAllPeople(int size, int page) {
        Page<PersonEntity> pageOfPeople = this.people
                .findAll(PageRequest.of(page, size, Direction.ASC, "name"));
        return pageOfPeople.getContent()
                .stream()
                .map(this::toBoundary)
                .collect(Collectors.toList());
    }

    public Person toBoundary(PersonEntity entity) {
        Person rv = new Person();

        rv.setId(entity.getId());
        rv.setName(entity.getName());
        rv.setBirthdate(LocalDate.ofEpochDay(entity.getBirthdateEpoc()));

        return rv;
    }

    public PersonEntity toEntity(Person boundary) {
        PersonEntity rv = new PersonEntity();

        rv.setId(boundary.getId());
        rv.setName(boundary.getName());
        rv.setBirthdateEpoc(boundary.getBirthdate().toEpochDay());

        return rv;
    }

}
