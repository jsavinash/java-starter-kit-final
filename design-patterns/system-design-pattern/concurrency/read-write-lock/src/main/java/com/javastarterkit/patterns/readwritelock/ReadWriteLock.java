package com.javastarterkit.patterns.readwritelock;

/**
 * Read-Write Lock Pattern
 * 
 * Allows concurrent reads, exclusive writes.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ReadWriteLock {
    
    static class ReadWriteLockImpl {
        private int readers = 0;
        private int writers = 0;
        private int writeRequests = 0;
        
        public synchronized void lockRead() throws InterruptedException {
            while (writers > 0 || writeRequests > 0) { wait(); }
            readers++;
        }
        
        public synchronized void unlockRead() {
            readers--;
            notifyAll();
        }
        
        public synchronized void lockWrite() throws InterruptedException {
            writeRequests++;
            while (readers > 0 || writers > 0) { wait(); }
            writeRequests--;
            writers++;
        }
        
        public synchronized void unlockWrite() {
            writers--;
            notifyAll();
        }
    }
    
    static class SharedData {
        private String data = "initial";
        private ReadWriteLockImpl lock = new ReadWriteLockImpl();
        
        public String read() throws InterruptedException {
            lock.lockRead();
            try {
                System.out.println("  Reading: " + data);
                return data;
            } finally {
                lock.unlockRead();
            }
        }
        
        public void write(String newData) throws InterruptedException {
            lock.lockWrite();
            try {
                System.out.println("  Writing: " + newData);
                data = newData;
            } finally {
                lock.unlockWrite();
            }
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Read-Write Lock Pattern ===");
        System.out.println("Allows concurrent reads, exclusive writes.\n");
        
        SharedData shared = new SharedData();
        
        try {
            shared.write("updated data");
            shared.read();
            shared.read();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
