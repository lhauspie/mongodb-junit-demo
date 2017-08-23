package com.github.lhauspie.mongodb.junit.demo.repository;

import com.github.lhauspie.mongodb.junit.demo.config.MockedMongoConfig;
import com.github.lhauspie.mongodb.junit.demo.domain.Product;
import com.github.lhauspie.mongodb.junit.demo.dto.SearchQuery;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(MockedMongoConfig.class)
public class ProductRepositoryUnitTest {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void searchProductTest() {
        // définition du comportement de la couche SGBD
        Query query = new Query();
        query.addCriteria(Criteria.where("code").regex("MON_CODE"));
        Mockito.when(mongoTemplate.find(Matchers.eq(query), Matchers.eq(Product.class)))
                .then(ignoredInvocation -> Arrays.asList(new Product("ID", "CODE", "LABEL", "DESCRIPTION", 1234D)));

        // appel de la couche Repository à tester
        SearchQuery searchQquery = new SearchQuery("MON_CODE", null, null, null, null);
        Collection<Product> products = productRepo.search(searchQquery);

        // verification du résultat
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).containsExactly(new Product("ID", "CODE", "LABEL", "DESCRIPTION", 1234D));
    }
}
