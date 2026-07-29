// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.elasticsearch.web;

import com.javastarterkit.elasticsearch.entity.ProductDocument;
import com.javastarterkit.elasticsearch.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDocument> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/search/{name}")
    public List<ProductDocument> searchProducts(@PathVariable String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/category/{category}")
    public List<ProductDocument> getByCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }
}
