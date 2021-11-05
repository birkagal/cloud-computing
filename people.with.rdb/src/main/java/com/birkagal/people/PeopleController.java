package com.birkagal.people;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PeopleController {
    private final PeopleService people;

    @Autowired
    public PeopleController(PeopleService people) {
        super();
        this.people = people;
    }

    @RequestMapping(path = "/people", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person store(@RequestBody Person newPerson) {
        return this.people.store(newPerson);
    }

    @RequestMapping(path = "/people/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person get(@PathVariable("id") String id) {
        return this.people.get(id);
    }

    @RequestMapping(path = "/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person[] getAll(@RequestParam(name = "size", required = false, defaultValue = "10") int size,
                           @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return this.people.getAll(page, size);
    }

    @RequestMapping(path = "/people/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") String id, @RequestBody Person updatedPerson) {
        this.people.update(id, updatedPerson);
    }

    @RequestMapping(path = "/people", method = RequestMethod.DELETE)
    public void deleteAll() {
        this.people.deleteAll();
    }
}
