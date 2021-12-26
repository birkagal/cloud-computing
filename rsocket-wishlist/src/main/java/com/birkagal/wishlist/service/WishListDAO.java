package com.birkagal.wishlist.service;

import com.birkagal.wishlist.model.WishListEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface WishListDAO extends ReactiveMongoRepository<WishListEntity, String> {

    Flux<WishListEntity> findByUserEmail(@Param("userEmail") String userEmail, Sort sort);
}
