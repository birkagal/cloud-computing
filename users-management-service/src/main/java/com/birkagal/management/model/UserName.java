package com.birkagal.management.model;

import com.birkagal.management.exception.InvalidInputException;

public class UserName implements Comparable<UserName> {

    private String first;
    private String last;

    public UserName() {
    }

    public UserName(String first, String last) {
        this.setFirst(first);
        this.setLast(last);
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        if (first.isEmpty())
            throw new InvalidInputException("First name cannot be empty.");

        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        if (last.isEmpty())
            throw new InvalidInputException("Last name cannot be empty.");

        this.last = last;
    }

    @Override
    public String toString() {
        return "UserName [first=" + first + ", last=" + last + "]";
    }

    @Override
    public int compareTo(UserName o) {
        // Compare two user names based on last name. If equal compare on first name
        int lastNameCompare = this.getLast().compareTo(o.getLast());
        return lastNameCompare == 0 ? this.getFirst().compareTo(o.getFirst()) : lastNameCompare;
    }
}
