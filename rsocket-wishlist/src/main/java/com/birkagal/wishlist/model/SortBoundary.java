package com.birkagal.wishlist.model;

import java.util.ArrayList;

public class SortBoundary {

    private ArrayList<String> sortBy;
    private String order;

    public SortBoundary() {
    }

    public SortBoundary(ArrayList<String> sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public ArrayList<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(ArrayList<String> sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SortBoundary{" +
                "sortBy=" + sortBy +
                ", order='" + order + '\'' +
                '}';
    }
}
