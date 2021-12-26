package com.birkagal.wishlist.service;

import com.birkagal.wishlist.model.*;

import java.util.ArrayList;
import java.util.Collections;

public class ServiceUtil {

    public WishListEntity wishlistToEntity(WishListBoundary boundary) {
        WishListEntity entity = new WishListEntity();
        entity.setWishListId(boundary.getWishListId());
        entity.setName(boundary.getName());
        entity.setUserEmail(boundary.getUserEmail());
        entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
        return entity;
    }

    public WishListBoundary wishlistToBoundary(WishListEntity entity) {
        WishListBoundary boundary = new WishListBoundary();
        boundary.setWishListId(entity.getWishListId());
        boundary.setName(entity.getName());
        boundary.setUserEmail(entity.getUserEmail());
        boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
        return boundary;
    }

    public WishListProductEntity wishlistProductToEntity(WishListProductBoundary boundary) {
        WishListProductEntity entity = new WishListProductEntity();
        entity.setWishListId(boundary.getWishListId());
        entity.setProductId(boundary.getProductId());
        return entity;
    }

    public WishListProductBoundary wishlistProductToBoundary(WishListProductEntity entity) {
        WishListProductBoundary boundary = new WishListProductBoundary();
        boundary.setWishListId(entity.getWishListId());
        boundary.setProductId(entity.getProductId());
        return boundary;
    }

    public SortBoundary validateSort(SortBoundary sort) {
        // Validate sort has correct fields. If not, use defaults
        SortBoundary validatedSort = new SortBoundary(
                new ArrayList<>(Collections.singletonList("wishListId")),
                "ASC");
        if (sort.getSortBy() != null)
            validatedSort.setSortBy(sort.getSortBy());
        if (sort.getOrder() != null)
            validatedSort.setOrder((sort.getOrder()));
        return validatedSort;
    }
}
