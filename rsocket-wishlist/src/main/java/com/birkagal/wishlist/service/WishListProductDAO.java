package com.birkagal.wishlist.service;

import com.birkagal.wishlist.model.WishListProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface WishListProductDAO extends ReactiveMongoRepository<WishListProductEntity, String> {

    Flux<WishListProductEntity> findAllByWishListId(@Param("wishListId") String wishListId, Sort sort);
}

