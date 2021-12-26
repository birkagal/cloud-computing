package com.birkagal.wishlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WISHLIST_PRODUCTS")
public class WishListProductEntity {

    private String id;
    private String wishListId;
    private String productId;

    public WishListProductEntity() {
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "WishlistProductEntity{" +
                "id='" + id + '\'' +
                ", wishlistId='" + wishListId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
