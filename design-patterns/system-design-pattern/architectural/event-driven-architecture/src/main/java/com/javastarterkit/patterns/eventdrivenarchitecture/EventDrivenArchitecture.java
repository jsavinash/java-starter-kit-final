package com.javastarterkit.patterns.eventdrivenarchitecture;

/**
 * Event-Driven Architecture Pattern Example
 * 
 * Produces, detects, and reacts to events in a decoupled manner.
 * Like an order processing system where events trigger actions.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class EventDrivenArchitecture {
    
    public static void demonstrate() {
        System.out.println("\n=== Event-Driven Architecture Pattern ===");
        System.out.println("Reacts to events in a decoupled manner\n");
        
        // Create event bus
        EventBus eventBus = new EventBus();
        
        // Create services
        OrderService orderService = new OrderService(eventBus);
        EmailService emailService = new EmailService(eventBus);
        InventoryService inventoryService = new InventoryService(eventBus);
        
        System.out.println("Placing order...");
        orderService.placeOrder("ORD-001", "john@example.com", 2);
        
        System.out.println("\nBenefits:");
        System.out.println("- Loose coupling between services");
        System.out.println("- Easy to add new event handlers");
        System.out.println("- Scalable and maintainable");
    }
    
    // Event interface
    interface Event {
    }
    
    static class OrderPlacedEvent implements Event {
        String orderId;
        String email;
        int quantity;
        
        public OrderPlacedEvent(String orderId, String email, int quantity) {
            this.orderId = orderId;
            this.email = email;
            this.quantity = quantity;
        }
    }
    
    // Event Bus
    static class EventBus {
        private java.util.Map<Class<? extends Event>, java.util.List<EventListener<? extends Event>>> listeners = 
            new java.util.HashMap<>();
        
        public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
            listeners.computeIfAbsent(eventType, k -> new java.util.ArrayList<>()).add(listener);
        }
        
        public <T extends Event> void publish(T event) {
            java.util.List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
            if (eventListeners != null) {
                for (EventListener<? extends Event> listener : eventListeners) {
                    ((EventListener<T>) listener).onEvent(event);
                }
            }
        }
    }
    
    // Event Listener interface
    interface EventListener<T extends Event> {
        void onEvent(T event);
    }
    
    // Services
    static class OrderService {
        private EventBus eventBus;
        
        public OrderService(EventBus eventBus) {
            this.eventBus = eventBus;
        }
        
        public void placeOrder(String orderId, String email, int quantity) {
            System.out.println("  Order placed: " + orderId);
            eventBus.publish(new OrderPlacedEvent(orderId, email, quantity));
        }
    }
    
    static class EmailService implements EventListener<OrderPlacedEvent> {
        private EventBus eventBus;
        
        public EmailService(EventBus eventBus) {
            this.eventBus = eventBus;
            eventBus.subscribe(OrderPlacedEvent.class, this);
        }
        
        @Override
        public void onEvent(OrderPlacedEvent event) {
            System.out.println("  EmailService: Sending confirmation to " + event.email);
        }
    }
    
    static class InventoryService implements EventListener<OrderPlacedEvent> {
        private EventBus eventBus;
        
        public InventoryService(EventBus eventBus) {
            this.eventBus = eventBus;
            eventBus.subscribe(OrderPlacedEvent.class, this);
        }
        
        @Override
        public void onEvent(OrderPlacedEvent event) {
            System.out.println("  InventoryService: Reserved " + event.quantity + " items for " + event.orderId);
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}