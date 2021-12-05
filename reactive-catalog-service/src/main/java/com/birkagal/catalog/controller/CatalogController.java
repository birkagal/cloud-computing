package com.birkagal.catalog.controller;

import com.birkagal.catalog.model.ProductBoundary;
import com.birkagal.catalog.service.CatalogServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class CatalogController {

    private final CatalogServiceImplementation catalog;

    @Autowired
    public CatalogController(CatalogServiceImplementation catalog) {
        super();
        this.catalog = catalog;
    }

    @RequestMapping(path = "/",
            method = RequestMethod.GET)
    public Mono<Void> homepage(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create("/swagger-ui.html"));
        return response.setComplete();
    }

    @RequestMapping(path = "/shopping",
            method = RequestMethod.DELETE)
    public Mono<Void> deleteAll() {
        return this.catalog
                .deleteAll();
    }

    @RequestMapping(path = "/shopping/products",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductBoundary> store(@RequestBody ProductBoundary newProduct) {
        return this.catalog
                .store(newProduct);
    }

    @RequestMapping(path = "/shopping/products/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductBoundary> getById(@PathVariable("productId") String productId) {
        return this.catalog
                .getById(productId);
    }

    @RequestMapping(path = "/shopping/products",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductBoundary> getAllFiltered(
            @RequestParam(required = false, defaultValue = "") String filterType,
            @RequestParam(required = false, defaultValue = "") String filterValue,
            @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return this.catalog
                .getAllFiltered(filterType, filterValue, sortOrder, sortBy);
    }
}
