package com.javastarterkit.patterns.repository;

/**
 * Repository Pattern
 * 
 * Mediates between domain and data mapping layers.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Repository {
    
    static class Product {
        private int id; private String name; private double price;
        public Product(int id, String name, double price) {
            this.id = id; this.name = name; this.price = price;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        @Override
        public String toString() { return "Product{id=" + id + ", name='" + name + "', price=$" + price + "}"; }
    }
    
    interface ProductRepository {
        Product findById(int id);
        java.util.List<Product> findAll();
        void save(Product product);
        void delete(int id);
    }
    
    static class InMemoryProductRepository implements ProductRepository {
        private java.util.Map<Integer, Product> products = new java.util.HashMap<>();
        @Override public Product findById(int id) { return products.get(id); }
        @Override public java.util.List<Product> findAll() { return new java.util.ArrayList<>(products.values()); }
        @Override public void save(Product p) { products.put(p.getId(), p); System.out.println("  Saved: " + p); }
        @Override public void delete(int id) { products.remove(id); System.out.println("  Deleted product " + id); }
    }
    
    public static void demonstrate() {
        System.out.println("=== Repository Pattern ===");
        System.out.println("Mediates between domain and data mapping layers.\n");
        
        ProductRepository repo = new InMemoryProductRepository();
        repo.save(new Product(1, "Laptop", 999.99));
        repo.save(new Product(2, "Mouse", 29.99));
        
        System.out.println("\nAll products:");
        for (Product p : repo.findAll()) { System.out.println("  " + p); }
        
        System.out.println("\nFind by ID:");
        System.out.println("  " + repo.findById(1));
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
