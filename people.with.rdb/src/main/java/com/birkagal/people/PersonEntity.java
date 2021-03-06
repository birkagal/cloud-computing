package com.birkagal.people;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PEOPLE_TBL")
public class PersonEntity {

    private Long id;
    private String name;
    private Date birthdate;

    public PersonEntity() {
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
