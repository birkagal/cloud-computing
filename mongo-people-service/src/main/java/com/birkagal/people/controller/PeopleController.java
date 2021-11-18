package com.birkagal.people.controller;

import com.birkagal.people.model.Person;
import com.birkagal.people.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class PeopleController {

    private final PeopleService people;

    @Autowired
    public PeopleController(PeopleService people) {
        super();
        this.people = people;
    }

    @RequestMapping(path = "/people",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person store(@RequestBody Person newPerson) {
        return this.people
                .store(newPerson);
    }

    @RequestMapping(path = "/people/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") String id, @RequestBody Person updatedPerson) {
        this.people.update(id, updatedPerson);
    }

    @RequestMapping(path = "/people/",
            method = RequestMethod.DELETE)
    public void deleteAll() {
        this.people.deleteAll();
    }

    @RequestMapping(path = "/people/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                           @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return this.people
                .getAll(page, size)
                .toArray(new Person[0]);
    }

    @RequestMapping(path = "/people/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person get(@PathVariable String id) {
        return this.people
                .get(id);
    }

    @RequestMapping(path = "/people/filter/{day}/{month}/{year}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getOldPeople(@PathVariable int day, @PathVariable int month, @PathVariable int year,
                                 @RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return this.people
                .findPeopleOlderThan(LocalDate.of(year, month, day).toEpochDay(), page, size)
                .toArray(new Person[0]);
    }

    @RequestMapping(path = "/people/filter/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getAllByName(@PathVariable String name,
                                 @RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return this.people
                .findAllByNameEquals(name, page, size)
                .toArray(new Person[0]);
    }
}
