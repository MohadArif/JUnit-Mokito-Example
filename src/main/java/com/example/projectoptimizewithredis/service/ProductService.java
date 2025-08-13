package com.example.projectoptimizewithredis.service;
import com.example.projectoptimizewithredis.entity.Product;
import com.example.projectoptimizewithredis.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    // Create: Clear all cache since new record changes the dataset
    @CacheEvict(value = "product", allEntries = true)
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    // Get All: Cache result
    @Cacheable(value = "product")
    public List<Product> findAll() {
        System.out.println("Fetching from DB...");
        return productRepo.findAll();
    }

    // Get by ID: Cache result
    @Cacheable(value = "product", key = "#id")
    public Product findById(Long id) {
        System.out.println("Fetching from DB...");
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not found"));
    }

    // Delete: Remove both list and single product from cache
    @CacheEvict(value = "product", key = "#id", allEntries = true)
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    // Update: Update the specific product cache and clear the list cache
    @CachePut(value = "product", key = "#id")
    @CacheEvict(value = "product", allEntries = true)
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not found"));
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setMfdDate(product.getMfdDate());
        existingProduct.setExpDate(product.getExpDate());
        return productRepo.save(existingProduct);
    }
}
