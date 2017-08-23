package com.github.lhauspie.mongodb.junit.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String code;
    private String label;
    private String description;
    private Double price;
}
