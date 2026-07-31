package com.javastarterkit.patterns.eventaggregator;

/**
 * Event Aggregator Pattern
 * 
 * Collects events from multiple sources and distributes them.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class EventAggregator {
    
    static class EventAggregatorImpl {
        private java.util.List<String> events = new java.util.ArrayList<>();
        private java.util.List<Runnable> listeners = new java.util.ArrayList<>();
        
        public void addEvent(String event) {
            events.add(event);
            System.out.println("  Aggregator received: " + event);
            notifyListeners();
        }
        
        public void addListener(Runnable listener) { listeners.add(listener); }
        private void notifyListeners() { listeners.forEach(Runnable::run); }
        public java.util.List<String> getEvents() { return new java.util.ArrayList<>(events); }
    }
    
    public static void demonstrate() {
        System.out.println("=== Event Aggregator Pattern ===");
        System.out.println("Collects events from multiple sources and distributes them.\n");
        
        EventAggregatorImpl aggregator = new EventAggregatorImpl();
        aggregator.addListener(() -> System.out.println("  UI: Updating display"));
        aggregator.addListener(() -> System.out.println("  Log: Writing to log file"));
        
        aggregator.addEvent("Button clicked");
        aggregator.addEvent("Data loaded");
        aggregator.addEvent("Error occurred");
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
