package com.example.projectoptimizewithredis.service;

import com.example.projectoptimizewithredis.entity.Product;
import com.example.projectoptimizewithredis.repository.ProductRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "rice", "basmati", 1250.0, "12/02/23", "23/05/26");

    }

    @AfterEach
    void tearDown() {
        product=null;
    }

    @Test
    void createProduct() {
        when(productRepo.save(product)).thenReturn(product);
        Product product1 = productService.createProduct(product);
        assertEquals(product,product1);
        verify(productRepo,times(1)).save(product);
    }

    @Test
    void findAll() {
        List<Product> productList=List.of(new Product(1L, "rice", "basmati", 1250.0, "12/02/23", "23/05/26"),
                new Product(1L, "sonam", "basmati", 1250.0, "12/02/23", "23/05/26"),
                new Product(1L, "nafis", "basmati", 1250.0, "12/02/23", "23/05/26"));
        when(productRepo.findAll()).thenReturn(productList);
        List<Product> productList1 = productService.findAll();
        assertEquals(productList,productList1);
        verify(productRepo,times(1)).findAll();
    }
    @Test
    void findById() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        Product productById = productService.findById(product.getId());
        assertEquals(productById,product);
        verify(productRepo,times(1)).findById(product.getId());
    }

    @Test
    void deleteById() {
        doNothing().when(productRepo).deleteById(product.getId());
        productService.deleteById(product.getId());
        verify(productRepo,times(1)).deleteById(product.getId());
    }

    @Test
    void updateProduct() {
        when(productRepo.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product newPrduct = new Product(1L, "sonam", "india gate", 1250.0, "12/02/23", "23/05/26");

        Product updateProduct = productService.updateProduct(product.getId(), newPrduct);

        assertEquals(product,updateProduct);
        verify(productRepo,times(1)).save(product);

    }
}