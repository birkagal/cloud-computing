package com.birkagal.people.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "people")
public class PersonEntity {
    private String id;
    private String name;
    private long birthdateEpoc;

    public PersonEntity() {
    }

    public PersonEntity(String name) {
        super();
        this.name = name;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthdateEpoc() {
        return birthdateEpoc;
    }

    public void setBirthdateEpoc(long birthdateEpoc) {
        this.birthdateEpoc = birthdateEpoc;
    }

    @Override
    public String toString() {
        return "PersonEntity [id=" + id + ", name=" + name + ", birthdateEpoc=" + birthdateEpoc + "]";
    }

}
