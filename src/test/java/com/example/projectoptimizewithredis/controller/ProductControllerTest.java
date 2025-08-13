package com.example.projectoptimizewithredis.controller;

import com.example.projectoptimizewithredis.entity.Product;
import com.example.projectoptimizewithredis.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createProduct() throws Exception {

        when(productService.createProduct(any(Product.class))).thenReturn(product);
        // Act & Assert
                mockMvc.perform(post("/api/product")
                                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))) // ✅ Object to JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("rice"))
                .andExpect(jsonPath("$.brand").value("basmati"));
    }

    @Test
    void findAllProduct() throws Exception {
        List<Product> productList=List.of(new Product(1L, "rice", "basmati", 1250.0, "12/02/23", "23/05/26"),
                new Product(2L, "sonam", "basmati", 1250.0, "12/02/23", "23/05/26"),
                new Product(3L, "nafis", "basmati", 1250.0, "12/02/23", "23/05/26"));
        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/api/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(productList.size()))
                .andExpect(jsonPath("$.[0].name").value("rice"))
                .andExpect(jsonPath("$.[1].name").value("sonam"));

    }

    @Test
    void findById() throws Exception {
        when(productService.findById(product.getId())).thenReturn(product);
        mockMvc.perform(get("/api/product/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("rice"));
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(productService).deleteById(product.getId());
        mockMvc.perform(delete("/api/product/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception {
       Product newProduct = new Product(1L, "Aata", "Ashriwad", 1250.0, "12/02/23", "23/05/26");
        when(productService.updateProduct(product.getId(),newProduct)).thenReturn(newProduct);

        mockMvc.perform(put("/api/product/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Aata"))
                .andExpect(jsonPath("$.brand").value("Ashriwad"));

    }
}