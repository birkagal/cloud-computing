package com.birkagal.management.service;

import com.birkagal.management.model.UserBoundary;
import com.birkagal.management.model.UserEntity;
import com.birkagal.management.model.UserName;
import com.birkagal.management.model.UserWithoutPasswordBoundary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServiceUtil {

    private final ObjectMapper jackson;

    public ServiceUtil() {
        super();
        this.jackson = new ObjectMapper();
    }

    public UserEntity fromBoundary(UserBoundary boundary) {
        UserEntity entity = new UserEntity();
        entity.setEmail(boundary.getEmail());
        entity.setPassword(boundary.getPassword());
        entity.setBirthdate(boundary.getBirthdate());

        // UserEntity name format: FirstName$LastName
        entity.setName(boundary.getName().getFirst() + "$" + boundary.getName().getLast());
        entity.setRoles(marshal(boundary.getRoles()));
        return entity;
    }

    public UserBoundary toBoundary(UserEntity entity) {
        UserBoundary boundary = new UserBoundary();
        boundary.setEmail(entity.getEmail());
        boundary.setPassword(entity.getPassword());
        boundary.setBirthdate(entity.getBirthdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        // UserEntity name format: FirstName$LastName
        String[] splitUserName = entity.getName().split("\\$");
        boundary.setName(new UserName(splitUserName[0], splitUserName[1]));

        // Found unmarshalling ArrayList method here:
        // https://www.baeldung.com/jackson-collection-array
        ArrayList<String> roles = unmarshal(entity.getRoles(), new TypeReference<ArrayList<String>>() {
        });
        boundary.setRoles(roles);
        return boundary;
    }

    public UserWithoutPasswordBoundary toNoPassword(UserBoundary user) {
        UserWithoutPasswordBoundary userWithoutPassword = new UserWithoutPasswordBoundary();
        userWithoutPassword.setEmail(user.getEmail());
        userWithoutPassword.setBirthdate(user.getBirthdate());
        userWithoutPassword.setName(user.getName());
        userWithoutPassword.setRoles(user.getRoles());
        return userWithoutPassword;
    }

    public <T> T unmarshal(String json, TypeReference<T> type) {
        try {
            return this.jackson.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String marshal(Object obj) {
        try {
            return this.jackson.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
