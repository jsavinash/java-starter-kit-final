package com.javastarterkit.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javastarterkit.mongodb.entity.ProductDocument;

@Repository
public interface ProductRepository extends MongoRepository<ProductDocument, String> {

    List<ProductDocument> findByCategory(String category);

    List<ProductDocument> findByAvailable(boolean available);

    List<ProductDocument> findByNameContainingIgnoreCase(String name);
}