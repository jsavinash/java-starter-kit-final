package com.javastarterkit.patterns.threadpool;

/**
 * Thread Pool Pattern
 * 
 * Manages a pool of reusable threads.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ThreadPool {
    
    static class WorkerThread implements Runnable {
        private String task;
        public WorkerThread(String task) { this.task = task; }
        
        @Override
        public void run() {
            System.out.println("  Executing: " + task + " on " + Thread.currentThread().getName());
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
    
    static class ThreadPoolManager {
        private java.util.concurrent.ExecutorService executor;
        
        public ThreadPoolManager(int poolSize) {
            executor = java.util.concurrent.Executors.newFixedThreadPool(poolSize);
            System.out.println("  Thread pool created with size: " + poolSize);
        }
        
        public void submitTask(String task) {
            executor.submit(new WorkerThread(task));
        }
        
        public void shutdown() {
            executor.shutdown();
            try {
                executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("  Thread pool shut down");
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Thread Pool Pattern ===");
        System.out.println("Manages a pool of reusable threads.\n");
        
        ThreadPoolManager pool = new ThreadPoolManager(3);
        
        for (int i = 1; i <= 5; i++) {
            pool.submitTask("Task-" + i);
        }
        
        pool.shutdown();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
