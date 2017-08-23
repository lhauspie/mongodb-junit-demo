package com.github.lhauspie.mongodb.junit.demo.repository;

import com.github.lhauspie.mongodb.junit.demo.domain.Product;
import com.github.lhauspie.mongodb.junit.demo.dto.SearchQuery;

import java.util.Collection;

public interface ProductCustomRepository {
    Collection<Product> search(SearchQuery query);
}
