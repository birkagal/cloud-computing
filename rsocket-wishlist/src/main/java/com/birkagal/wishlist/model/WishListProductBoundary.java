package com.birkagal.wishlist.model;

public class WishListProductBoundary {

    private String wishListId;
    private String productId;

    public WishListProductBoundary() {
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
        return "WishListProductBoundary{" +
                "wishListId='" + wishListId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
