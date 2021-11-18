package com.birkagal.firstmongoservice.people;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class PeopleController {
    private PeopleService people;

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


    @RequestMapping(path = "/people/old/{day}/{month}/{year}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getOldPeople(int day, int month, int year,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "0") int page) {
        return this.people
                .findPeopleOlderThan(LocalDate.of(year, month, day).toEpochDay(), size, page)
                .toArray(new Person[0]);
    }

    @RequestMapping(path = "/people/name/{pattern}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getNamesLike(String pattern,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "0") int page) {
        return this.people
                .findAllByNameEquals(pattern, size, page)
                .toArray(new Person[0]);
    }

    @RequestMapping(path = "/people/",
            method = RequestMethod.DELETE)
    public void deleteAllPeople() {
        this.people.deleteAll();
    }

    @RequestMapping(path = "/people/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getAllPeople(@RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "0") int page) {
        return this.people
                .getAllPeople(size, page)
                .toArray(new Person[0]);
    }
}
