package com.github.lhauspie.mongodb.junit.demo.repository;

import com.github.lhauspie.mongodb.junit.demo.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductCustomRepository {
    Product findForUpdateById(String id);
}
