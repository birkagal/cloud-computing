package com.birkagal.people.service;

import com.birkagal.people.model.Person;
import com.birkagal.people.model.PersonEntity;

import java.time.LocalDate;

public class ServiceUtil {

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
