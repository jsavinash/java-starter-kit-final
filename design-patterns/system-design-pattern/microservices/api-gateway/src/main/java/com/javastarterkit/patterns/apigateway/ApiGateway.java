package com.javastarterkit.patterns.apigateway;

/**
 * API Gateway Pattern Example
 * 
 * Single entry point for multiple microservices, handling routing and cross-cutting concerns.
 * Like a front desk that directs visitors to different departments.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ApiGateway {
    
    public static void demonstrate() {
        System.out.println("\n=== API Gateway Pattern ===");
        System.out.println("Single entry point for microservices\n");
        
        ApiGateway gateway = new ApiGateway();
        
        System.out.println("Client requests:");
        gateway.handleRequest("/api/users", "GET");
        gateway.handleRequest("/api/orders", "POST");
        gateway.handleRequest("/api/products", "GET");
        
        System.out.println("\nBenefits:");
        System.out.println("- Single entry point");
        System.out.println("- Request routing and composition");
        System.out.println("- Cross-cutting concerns (auth, logging)");
    }
    
    interface Microservice {
        void handle(String request);
    }
    
    static class UserService implements Microservice {
        @Override
        public void handle(String request) {
            System.out.println("  UserService: Handling " + request);
        }
    }
    
    static class OrderService implements Microservice {
        @Override
        public void handle(String request) {
            System.out.println("  OrderService: Handling " + request);
        }
    }
    
    static class ProductService implements Microservice {
        @Override
        public void handle(String request) {
            System.out.println("  ProductService: Handling " + request);
        }
    }
    
    static class ApiGateway {
        private java.util.Map<String, Microservice> routes = new java.util.HashMap<>();
        
        public ApiGateway() {
            routes.put("/api/users", new UserService());
            routes.put("/api/orders", new OrderService());
            routes.put("/api/products", new ProductService());
        }
        
        public void handleRequest(String path, String method) {
            Microservice service = routes.get(path);
            if (service != null) {
                System.out.println("  Gateway: Routing " + method + " " + path);
                service.handle(method + " " + path);
            } else {
                System.out.println("  Gateway: 404 Not Found - " + path);
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}