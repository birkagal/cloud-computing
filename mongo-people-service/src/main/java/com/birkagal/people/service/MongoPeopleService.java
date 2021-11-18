package com.birkagal.people.service;

import com.birkagal.people.model.Person;
import com.birkagal.people.model.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MongoPeopleService implements PeopleService {

    private final PeopleDao people;
    private final ServiceUtil util;

    @Autowired
    public MongoPeopleService(PeopleDao people) {
        super();
        this.people = people;
        this.util = new ServiceUtil();
    }

    @Override
    public Person store(Person newPerson) {
        newPerson.setId(null);

        PersonEntity entity = this.util.toEntity(newPerson);
        entity = this.people
                .save(entity);

        return this.util.toBoundary(entity);
    }

    @Override
    public void update(String id, Person updatedPerson) {
        Optional<PersonEntity> op = this.people.findById(id);
        if (op.isEmpty())
            throw new RuntimeException("No person with id: " + id);

        PersonEntity entity = op.get();

        if (updatedPerson.getBirthdate() != null)
            entity.setBirthdateEpoc(updatedPerson.getBirthdate().toEpochDay());
        if (updatedPerson.getName() != null)
            entity.setName(updatedPerson.getName());


        this.people.save(entity);
    }

    @Override
    public void deleteAll() {
        this.people.deleteAll();
    }

    @Override
    public Person get(String id) {
        Optional<PersonEntity> op = this.people.findById(id);
        if (op.isEmpty())
            throw new RuntimeException("No person with that id: " + id);
        return this.util.toBoundary(op.get());
    }

    @Override
    public List<Person> getAll(int page, int size) {
        Page<PersonEntity> pageOfPeople = this.people
                .findAll(PageRequest.of(page, size, Direction.ASC, "name"));
        return pageOfPeople.getContent()
                .stream()
                .map(util::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findPeopleOlderThan(long value, int page, int size) {
        return this.people
                .findAllByBirthdateEpocLessThan(value, PageRequest.of(page, size, Direction.ASC, "birthdateEpoc"))
                .stream()
                .map(util::toBoundary)
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findAllByNameEquals(String name, int page, int size) {
        return this.people
                .findAllByNameEquals(name, PageRequest.of(page, size, Direction.ASC, "name"))
                .stream()
                .map(util::toBoundary)
                .collect(Collectors.toList());
    }
}
