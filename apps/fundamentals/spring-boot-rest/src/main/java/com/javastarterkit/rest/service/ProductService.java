// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.rest.service;

import com.javastarterkit.rest.dto.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * Service class demonstrating REST API patterns with in-memory storage.
 */
@Service
public class ProductService {

    private final ConcurrentHashMap<Long, ProductDTO> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductService() {
        // Initialize with sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        createProduct(new ProductDTO(null, "Laptop", "High-performance laptop", 999.99, 50, "Electronics"));
        createProduct(new ProductDTO(null, "Mouse", "Wireless mouse", 29.99, 200, "Electronics"));
        createProduct(new ProductDTO(null, "Keyboard", "Mechanical keyboard", 149.99, 100, "Electronics"));
    }

    public List<ProductDTO> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public ProductDTO getProductById(Long id) {
        return products.get(id);
    }

    public ProductDTO createProduct(ProductDTO product) {
        Long id = idGenerator.getAndIncrement();
        product.setId(id);

        // Apply business rules
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be 0 or greater");
        }

        products.put(id, product);
        return product;
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDetails) {
        ProductDTO existingProduct = products.get(id);
        if (existingProduct == null) {
            return null;
        }

        // Update fields
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }
        if (productDetails.getPrice() != null) {
            existingProduct.setPrice(productDetails.getPrice());
        }
        if (productDetails.getQuantity() != null) {
            existingProduct.setQuantity(productDetails.getQuantity());
        }
        if (productDetails.getCategory() != null) {
            existingProduct.setCategory(productDetails.getCategory());
        }

        return existingProduct;
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }

    public List<ProductDTO> searchProductsByName(String name) {
        List<ProductDTO> result = new ArrayList<>();
        for (ProductDTO product : products.values()) {
            if (product.getName() != null && product.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        List<ProductDTO> result = new ArrayList<>();
        for (ProductDTO product : products.values()) {
            if (category.equalsIgnoreCase(product.getCategory())) {
                result.add(product);
            }
        }
        return result;
    }
}
