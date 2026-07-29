// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.cassandra.service;

import com.javastarterkit.cassandra.entity.Product;
import com.javastarterkit.cassandra.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
