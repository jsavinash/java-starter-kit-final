package com.javastarterkit.hateoas.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javastarterkit.hateoas.assembler.ProductModelAssembler;
import com.javastarterkit.hateoas.entity.Product;

import org.springframework.hateoas.EntityModel;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>();
    private final ProductModelAssembler assembler;

    public ProductController(ProductModelAssembler assembler) {
        this.assembler = assembler;
        // Initialize sample data
        products.add(new Product(1L, "Laptop", 999.99));
        products.add(new Product(2L, "Mouse", 29.99));
        products.add(new Product(3L, "Keyboard", 79.99));
    }

    @GetMapping
    public List<EntityModel<Product>> getAllProducts() {
        return products.stream()
                .map(assembler::toModel)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable Long id) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(assembler.toModel(product));
    }
}