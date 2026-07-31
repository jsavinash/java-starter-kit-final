package com.javastarterkit.patterns.observer;

/**
 * Observer Pattern Example
 * 
 * Defines a one-to-many dependency between objects so that when one object changes state,
 * all its dependents are notified automatically.
 * Like a newsletter subscription service.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Observer {
    
    public static void demonstrate() {
        System.out.println("\n=== Observer Pattern ===");
        System.out.println("Notifies multiple objects about state changes\n");
        
        // Create news agency (subject)
        NewsAgency newsAgency = new NewsAgency();
        
        // Create subscribers (observers)
        Subscriber alice = new EmailSubscriber("Alice");
        Subscriber bob = new EmailSubscriber("Bob");
        Subscriber charlie = new SMSSubscriber("Charlie");
        
        // Subscribe to news
        System.out.println("Subscribing users to news agency...");
        newsAgency.subscribe(alice);
        newsAgency.subscribe(bob);
        newsAgency.subscribe(charlie);
        
        // Publish news
        System.out.println("\nPublishing breaking news...");
        newsAgency.setNews("BREAKING: Observer Pattern Implemented!");
        
        // Unsubscribe one user
        System.out.println("\nUnsubscribing Bob...");
        newsAgency.unsubscribe(bob);
        
        // Publish more news
        System.out.println("\nPublishing another news...");
        newsAgency.setNews("UPDATE: More patterns coming soon!");
        
        System.out.println("\nBenefits:");
        System.out.println("- Loose coupling between subject and observers");
        System.out.println("- Supports broadcast communication");
        System.out.println("- Easy to add/remove observers at runtime");
    }
    
    // Observer interface
    interface Subscriber {
        void update(String news);
    }
    
    // Concrete observers
    static class EmailSubscriber implements Subscriber {
        private String name;
        
        public EmailSubscriber(String name) {
            this.name = name;
        }
        
        @Override
        public void update(String news) {
            System.out.println("  [Email to " + name + "] " + news);
        }
    }
    
    static class SMSSubscriber implements Subscriber {
        private String name;
        
        public SMSSubscriber(String name) {
            this.name = name;
        }
        
        @Override
        public void update(String news) {
            System.out.println("  [SMS to " + name + "] " + news);
        }
    }
    
    // Subject interface
    interface NewsPublisher {
        void subscribe(Subscriber subscriber);
        void unsubscribe(Subscriber subscriber);
        void notifySubscribers();
    }
    
    // Concrete subject
    static class NewsAgency implements NewsPublisher {
        private String news;
        private java.util.List<Subscriber> subscribers = new java.util.ArrayList<>();
        
        @Override
        public void subscribe(Subscriber subscriber) {
            subscribers.add(subscriber);
            System.out.println("  New subscriber added");
        }
        
        @Override
        public void unsubscribe(Subscriber subscriber) {
            subscribers.remove(subscriber);
            System.out.println("  Subscriber removed");
        }
        
        public void setNews(String news) {
            this.news = news;
            notifySubscribers();
        }
        
        @Override
        public void notifySubscribers() {
            System.out.println("  Notifying all subscribers...");
            for (Subscriber subscriber : subscribers) {
                subscriber.update(news);
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}