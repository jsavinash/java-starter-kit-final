package com.javastarterkit.elasticsearch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javastarterkit.elasticsearch.entity.ProductDocument;
import com.javastarterkit.elasticsearch.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDocument save(ProductDocument product) {
        return productRepository.save(product);
    }

    public List<ProductDocument> findAll() {
        return productRepository.findAll();
    }

    public List<ProductDocument> searchByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<ProductDocument> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}