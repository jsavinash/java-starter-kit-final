package com.javastarterkit.patterns.producerconsumer;

/**
 * Producer-Consumer Pattern Example
 * 
 * Separates producers and consumers of data using a shared buffer.
 * Like a bakery where bakers produce and waiters consume orders.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ProducerConsumer {
    
    public static void demonstrate() {
        System.out.println("\n=== Producer-Consumer Pattern ===");
        System.out.println("Separates producers and consumers\n");
        
        SharedBuffer buffer = new SharedBuffer(5);
        
        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nBenefits:");
        System.out.println("- Decouples producers from consumers");
        System.out.println("- Handles different production/consumption rates");
        System.out.println("- Thread-safe data sharing");
    }
    
    interface Buffer {
        void produce(int item) throws InterruptedException;
        int consume() throws InterruptedException;
    }
    
    static class SharedBuffer implements Buffer {
        private java.util.Queue<Integer> items = new java.util.LinkedList<>();
        private int capacity;
        
        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }
        
        @Override
        public synchronized void produce(int item) throws InterruptedException {
            while (items.size() == capacity) {
                wait();
            }
            items.add(item);
            System.out.println("  Produced: " + item);
            notifyAll();
        }
        
        @Override
        public synchronized int consume() throws InterruptedException {
            while (items.isEmpty()) {
                wait();
            }
            int item = items.poll();
            System.out.println("  Consumed: " + item);
            notifyAll();
            return item;
        }
    }
    
    static class Producer implements Runnable {
        private Buffer buffer;
        
        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    static class Consumer implements Runnable {
        private Buffer buffer;
        
        public Consumer(Buffer buffer) {
            this.buffer = buffer;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}