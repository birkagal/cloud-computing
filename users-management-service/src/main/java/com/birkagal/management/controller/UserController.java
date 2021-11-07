package com.birkagal.management.controller;

import com.birkagal.management.model.UserBoundary;
import com.birkagal.management.model.UserWithoutPasswordBoundary;
import com.birkagal.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @RequestMapping(path = "/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserWithoutPasswordBoundary store(@RequestBody UserBoundary newUser) {
        return this.userService.store(newUser);
    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserWithoutPasswordBoundary get(@PathVariable("email") String email) {
        return this.userService.get(email);
    }

    @RequestMapping(path = "/users/login/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserWithoutPasswordBoundary login(@PathVariable("email") String email,
                                             @RequestParam(name = "password", required = false, defaultValue = "") String password) {
        return this.userService.login(email, password);
    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@PathVariable("email") String email, @RequestBody UserBoundary updatedUser) {
        this.userService.update(email, updatedUser);
    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.DELETE)
    public void deleteAll(@PathVariable("email") String email) {
        this.userService.delete(email);
    }

    @RequestMapping(path = "/users",
            method = RequestMethod.DELETE)
    public void deleteAll() {
        this.userService.deleteAll();
    }

    @RequestMapping(path = "/users/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserWithoutPasswordBoundary[] getAll(
            @RequestParam(name = "criteriaType", required = false, defaultValue = "") String criteriaType,
            @RequestParam(name = "criteriaValue", required = false, defaultValue = "") String criteriaValue,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String sortOrder) {
        return this.userService.getAllFiltered(criteriaType, criteriaValue, size, page, sortBy, sortOrder);
    }
}
