// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.rest.web;

import com.javastarterkit.rest.dto.ProductDTO;
import com.javastarterkit.rest.exception.ResourceNotFoundException;
import com.javastarterkit.rest.service.ProductService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller demonstrating validation and best practices.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        List<ProductDTO> products = productService.getAllProducts();
        response.put("products", products);
        response.put("count", products.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        ProductDTO product = productService.getProductById(id);
        if (product != null) {
            response.put("product", product);
            return ResponseEntity.ok(response);
        }
        throw new ResourceNotFoundException("Product not found with id: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody ProductDTO product) {
        Map<String, Object> response = new HashMap<>();
        ProductDTO createdProduct = productService.createProduct(product);
        response.put("product", createdProduct);
        response.put("message", "Product created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductDTO productDetails) {
        Map<String, Object> response = new HashMap<>();
        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            response.put("product", updatedProduct);
            response.put("message", "Product updated successfully");
            return ResponseEntity.ok(response);
        }
        throw new ResourceNotFoundException("Product not found with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        }
        throw new ResourceNotFoundException("Product not found with id: " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        List<ProductDTO> products = productService.searchProductsByName(name);
        response.put("products", products);
        response.put("count", products.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(@PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        List<ProductDTO> products = productService.getProductsByCategory(category);
        response.put("products", products);
        response.put("count", products.size());
        return ResponseEntity.ok(response);
    }
}
