package com.birkagal.catalog.service;

import com.birkagal.catalog.model.ProductBoundary;
import com.birkagal.catalog.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogServiceImplementation implements CatalogService {

    private final CatalogDAO catalog;
    private final ServiceUtil util;

    @Autowired
    public CatalogServiceImplementation(CatalogDAO catalog) {
        super();
        this.catalog = catalog;
        this.util = new ServiceUtil();
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.catalog
                .deleteAll();
    }

    @Override
    public Mono<ProductBoundary> store(ProductBoundary newProduct) {
        return Mono.just(newProduct) // Mono<ProductBoundary>
                .map(boundary -> {
                    // TODO: check if user provided id, if not throw 404
                    return boundary;
                }) // Mono<ProductBoundary>
                .map(this.util::toEntity) // Mono<ProductEntity>
                // TODO: check if ID already in database
                .flatMap(this.catalog::save) // Mono<ProductEntity>
                .map(this.util::toBoundary) // Mono<ProductBoundary>
                .log(); // Mono<ProductBoundary>
    }

    @Override
    public Flux<ProductBoundary> getAllFiltered(String filterType, String filterValue, String sortOrder, String sortBy) {
        /*
        // Validate sortOrder is ASC/DESC only
        if (!sortOrder.isEmpty() && !sortOrder.equals("ASC") && !sortOrder.equals("DESC"))
        // TODO: throw 404
        {
            // throw new InvalidInputException("Invalid sortOrder. Use ASC/DESC or none. Received: " + sortOrder);
        }
        // Validate sortBy is email/name/birthdate/roles
        sortBy = sortBy.toLowerCase();
        if (!sortBy.isEmpty() && !sortBy.equals("id") && !sortBy.equals("name") && !sortBy.equals("price")
                && !sortBy.equals("category") && !sortBy.equals("image") && !sortBy.equals("productdetails"))
        // TODO: throw 404
        {
            //throw new InvalidInputException("Invalid sortBy. Use email/name/birthdate/roles or none. Received: " + sortBy);
        }
         */

        Flux<ProductEntity> rv = Flux.empty();
        double price = 0.0;

        switch (filterType.toLowerCase()) {
            case "":
                rv = this.catalog
                        .findAll(Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
                break;
            case "byname":
                rv = this.catalog
                        .findAllByName(filterValue, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
                break;
            case "byminprice":
                try {
                    price = Double.parseDouble(filterValue);
                } catch (Exception e) {
                    // TODO: throw 404 error
                }
                rv = this.catalog
                        .findAllByPriceGreaterThanEqual(price, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
                break;
            case "bymaxprice":
                try {
                    price = Double.parseDouble(filterValue);
                } catch (Exception e) {
                    // TODO: throw 404 error
                }
                rv = this.catalog
                        .findAllByPriceLessThanEqual(price, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
                break;
            case "bycategoryname":
                rv = this.catalog
                        .findAllByCategory(filterValue, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
                break;
            default:
                /*
                // TODO: throw 404
                // throw new InvalidInputException(
                // "Invalid criteriaType. Use byEmailDomain/byBirthYear/byRole or none. Received: " + criteriaType);
                */
        }

        return rv
                .map(this.util::toBoundary)
                .log();
    }

    @Override
    public Mono<ProductBoundary> getById(String productId) {
        return this.catalog
                .findById(productId)
                .map(this.util::toBoundary)
                .log();
    }
}