package com.github.lhauspie.mongodb.junit.demo.repository.impl;

import com.github.lhauspie.mongodb.junit.demo.domain.Product;
import com.github.lhauspie.mongodb.junit.demo.dto.SearchQuery;
import com.github.lhauspie.mongodb.junit.demo.repository.ProductCustomRepository;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;


public class ProductRepositoryImpl implements ProductCustomRepository {

    public static final String CODE = "code";
    public static final String LABEL = "label";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;

    @Override
    public Collection<Product> search(SearchQuery searchQuery) {
        Query query = new Query();

        if (searchQuery.getCode() != null) {
            query.addCriteria(Criteria.where(CODE).regex(searchQuery.getCode()));
        }

        if (searchQuery.getLabel() != null) {
            query.addCriteria(Criteria.where(LABEL).regex(searchQuery.getLabel()));
        }

        if (searchQuery.getDescription() != null) {
            query.addCriteria(Criteria.where(DESCRIPTION).regex(searchQuery.getDescription()));
        }

        if (searchQuery.getMinPrice() != null || searchQuery.getMaxPrice() != null) {
            Criteria dutyFreePriceCriteria = Criteria.where(PRICE);
            if (searchQuery.getMinPrice() != null) {
                dutyFreePriceCriteria = dutyFreePriceCriteria.gt(searchQuery.getMinPrice());
            }
            if (searchQuery.getMaxPrice() != null) {
                dutyFreePriceCriteria = dutyFreePriceCriteria.lt(searchQuery.getMaxPrice());
            }
            query.addCriteria(dutyFreePriceCriteria);
        }

        return mongoTemplate.find(query, Product.class);
    }
}
