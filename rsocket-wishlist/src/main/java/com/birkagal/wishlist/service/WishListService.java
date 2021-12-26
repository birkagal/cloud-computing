package com.birkagal.wishlist.service;

import com.birkagal.wishlist.model.SortBoundary;
import com.birkagal.wishlist.model.UserBoundary;
import com.birkagal.wishlist.model.WishListBoundary;
import com.birkagal.wishlist.model.WishListProductBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WishListService {

    Mono<WishListBoundary> store(WishListBoundary wishList);

    Mono<Void> addProduct(WishListProductBoundary wishListProduct);

    Flux<WishListBoundary> getAllWishLists(SortBoundary sort);

    Flux<WishListProductBoundary> getAllProductsByWishList(WishListBoundary wishList);

    Mono<Void> deleteAll();

    Flux<WishListBoundary> getAllWishListsByEmail(UserBoundary user);

    Flux<WishListProductBoundary> getAllProducts(SortBoundary sort);

    Flux<WishListProductBoundary> getAllProductsByWishListChannel(Flux<WishListBoundary> wishList);
}
