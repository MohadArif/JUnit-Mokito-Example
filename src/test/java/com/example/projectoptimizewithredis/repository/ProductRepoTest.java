package com.example.projectoptimizewithredis.repository;

import com.example.projectoptimizewithredis.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;
    Product product;

    @BeforeEach
    void setUp() {
         product = new Product(null, "rice", "basmati", 1250.0, "12/02/23", "23/05/26");
        productRepo.save(product);
    }

    @AfterEach
    void tearDown() {
        productRepo.deleteAll();
    }

    @Test
    void testFindProductById(){
        Optional<Product> product1 = productRepo.findById(product.getId());
        assertTrue(product1.isPresent());
        assertEquals("rice",product1.get().getName());
    }

    @Test
    void testDeleteById(){
        productRepo.deleteById(product.getId());
        assertTrue(productRepo.findById(product.getId()).isEmpty());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Product> productOpt = productRepo.findById(999L);
        assertTrue(productOpt.isEmpty());
    }
}