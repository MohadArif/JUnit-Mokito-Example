package com.example.projectoptimizewithredis.controller;

import com.example.projectoptimizewithredis.entity.Product;
import com.example.projectoptimizewithredis.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        Product product1 = productService.createProduct(product);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllProduct(){
        List<Product> allProduct = productService.findAll();
        return new ResponseEntity<>(allProduct,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Product product = productService.findById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody Product product){
        Product product1 = productService.updateProduct(id, product);
        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }
}
