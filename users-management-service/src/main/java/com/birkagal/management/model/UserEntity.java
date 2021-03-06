package com.birkagal.management.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    private String email;
    private String name;
    private String password;
    private LocalDate birthdate;
    private String roles;

    public UserEntity() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.DATE)
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        // Birthdate is already validated in UserBoundary, no need to validate again
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}