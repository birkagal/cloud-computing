package com.birkagal.wishlist.model;

import java.util.Date;

public class WishListBoundary {

    private String wishListId;
    private String name;
    private String userEmail;
    private Date createdTimestamp;

    public WishListBoundary() {
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String toString() {
        return "WishlistBoundary{" +
                "wishListId='" + wishListId + '\'' +
                ", name='" + name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                '}';
    }
}
