package com.javastarterkit.modulith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class ModulithApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulithApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/inventory")
class InventoryController {

    @GetMapping
    public String getInventory() {
        return "Inventory module - products in stock";
    }
}