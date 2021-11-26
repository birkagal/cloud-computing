package com.birkagal.catalog.service;

import com.birkagal.catalog.model.ProductBoundary;
import com.birkagal.catalog.model.ProductEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ServiceUtil {

    private final ObjectMapper jackson;

    public ServiceUtil() {
        super();
        this.jackson = new ObjectMapper();
    }

    public ProductEntity toEntity(ProductBoundary boundary) {
        ProductEntity entity = new ProductEntity();
        entity.setId(boundary.getId());
        entity.setName(boundary.getName());
        entity.setPrice(boundary.getPrice());
        entity.setImage(boundary.getImage());
        entity.setCategory(boundary.getCategory());
        entity.setProductDetails(marshal(boundary.getProductDetails()));
        return entity;
    }

    public ProductBoundary toBoundary(ProductEntity entity) {
        ProductBoundary boundary = new ProductBoundary();
        boundary.setId(entity.getId());
        boundary.setName(entity.getName());
        boundary.setPrice(entity.getPrice());
        boundary.setImage(entity.getImage());
        boundary.setCategory(entity.getCategory());

        Map<String, Object> details = unmarshal(entity.getProductDetails(), new TypeReference<Map<String, Object>>() {
        });
        boundary.setProductDetails(details);
        return boundary;
    }

    public <T> T unmarshal(String json, TypeReference<T> type) {
        try {
            return this.jackson.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String marshal(Object obj) {
        try {
            return this.jackson.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}