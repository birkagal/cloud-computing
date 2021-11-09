package com.birkagal.management.model;

import com.birkagal.management.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserBoundary {

    private String email;
    private UserName name;
    private String password;
    private String birthdate;
    private ArrayList<String> roles;

    public UserBoundary() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Validate email is valid format: name@domain.tld
        if (!Pattern.compile("[A-Z0-9_.]+@([A-Z0-9]+\\.)+[A-Z0-9]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email)
                .find())
            throw new InvalidInputException("Invalid email: " + email);

        this.email = email;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 5)
            throw new InvalidInputException("Password must contain at least 5 characters.");
        // Validate password has at least 1 digit in it
        if (!Pattern.compile("\\p{Nd}").matcher(password).find())
            throw new InvalidInputException("Password must contain at least one digit.");

        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        /*
         * In order to validate birthdate, we try to convert it to LocalDate with custom
         * formatter: dd-MM-yyyy. If the parsing failed (throw exception) the input is
         * not in the correct format.
         */
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(birthdate, formatter);
            this.birthdate = birthdate;
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid birthdate: " + birthdate + " Enter in format dd-MM-yyyy.");
        }
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        if (roles.isEmpty())
            throw new InvalidInputException("Roles must have at least one element.");

        this.roles = new ArrayList<>();
        for (String role : roles)
            // Add role to roles if its valid (non empty string)
            if (!role.isEmpty())
                this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", password=" + password + ", birthdate=" + birthdate
                + ", roles=" + roles + "]";
    }
}
