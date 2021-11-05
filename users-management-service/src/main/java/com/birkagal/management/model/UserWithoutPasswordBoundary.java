package com.birkagal.management.model;

import java.util.ArrayList;

public class UserWithoutPasswordBoundary {
    /*
     * There are no input validation here because this class in only used to convert
     * (the validated) User objects
     */
    private String email;
    private UserName name;
    private String birthdate;
    private ArrayList<String> roles;

    public UserWithoutPasswordBoundary() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", birthdate=" + birthdate + ", roles=" + roles + "]";
    }
}
