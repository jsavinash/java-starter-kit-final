// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.restdocs.controller;

import com.javastarterkit.restdocs.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>();

    public ProductController() {
        // Initialize with sample data
        products.add(new Product(1L, "Laptop", 999.99));
        products.add(new Product(2L, "Mouse", 29.99));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product =
                products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        products.add(product);
        return ResponseEntity.ok(product);
    }
}
