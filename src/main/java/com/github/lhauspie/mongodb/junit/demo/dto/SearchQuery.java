package com.github.lhauspie.mongodb.junit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchQuery {
    private String code;
    private String label;
    private String description;
    private Double minPrice;
    private Double maxPrice;
}
