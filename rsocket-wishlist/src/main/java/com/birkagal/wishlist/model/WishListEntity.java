package com.birkagal.wishlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "WISHLISTS")
public class WishListEntity {

    private String wishListId;
    private String name;
    private String userEmail;
    private Date createdTimestamp;

    public WishListEntity() {
    }

    @Id
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
        return "WishlistWithProductEntity{" +
                "wishListId='" + wishListId + '\'' +
                ", name='" + name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                '}';
    }
}
