package com.birkagal.catalog.service;

import com.birkagal.catalog.model.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CatalogDAO extends ReactiveMongoRepository<ProductEntity, String> {

    Flux<ProductEntity> findAllByName(String name, Sort sort);

    Flux<ProductEntity> findAllByPriceGreaterThanEqual(double price, Sort sort);

    Flux<ProductEntity> findAllByPriceLessThanEqual(double price, Sort sort);

    Flux<ProductEntity> findAllByCategory(String category, Sort sort);
}
