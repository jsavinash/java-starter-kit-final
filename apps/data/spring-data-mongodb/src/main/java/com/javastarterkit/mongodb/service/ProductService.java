// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.mongodb.service;

import com.javastarterkit.mongodb.entity.ProductDocument;
import com.javastarterkit.mongodb.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDocument createProduct(ProductDocument product) {
        return productRepository.save(product);
    }

    @Transactional
    public ProductDocument updateProduct(String id, ProductDocument updatedProduct) {
        ProductDocument existing =
                productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found: " + id));
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());
        existing.setAvailable(updatedProduct.isAvailable());
        return productRepository.save(existing);
    }

    @Transactional
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<ProductDocument> findAllProducts() {
        return productRepository.findAll();
    }

    public ProductDocument findById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public List<ProductDocument> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDocument> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}
