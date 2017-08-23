package com.github.lhauspie.mongodb.junit.demo.repository;

import com.github.lhauspie.mongodb.junit.demo.domain.Product;
import com.github.lhauspie.mongodb.junit.demo.dto.SearchQuery;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepo;

    @SpyBean
    private MongoTemplate mongoTemplate;

    @Test
    public void searchProductTest() {
        productRepo.deleteAll();
        // partie init de données
        Assertions.assertThat(productRepo.findAll()).hasSize(0);
        Product product1 = productRepo.save(new Product(null, "CODE", "LABEL", "DESCRIPTION", 1234D));
        Product product2 = productRepo.save(new Product(null, "CODE_LONG", "LABEL", "DESCRIPTION", 1234D));

        // appel de la couche Repository à tester
        Collection<Product> products = productRepo.search(new SearchQuery("CODE", null, null, null, null));

        // vérification de la bonne exécution de la méthode find du MongoTemplate avec les bons paramètres
        Query query = new Query();
        query.addCriteria(Criteria.where("code").regex("CODE"));
        Mockito.verify(mongoTemplate, Mockito.times(1)).find(query, Product.class);

        // vérification du résultat de l'execution
        Assertions.assertThat(products).hasSize(2);
        Assertions.assertThat(products).containsExactly(product1, product2);
    }
}
