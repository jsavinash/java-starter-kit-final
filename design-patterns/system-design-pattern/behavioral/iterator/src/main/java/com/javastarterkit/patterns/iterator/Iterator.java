package com.javastarterkit.patterns.iterator;

/**
 * Iterator Pattern
 * 
 * Provides a way to access elements sequentially without exposing the underlying structure.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Iterator {
    
    // Iterator interface
    interface IteratorInterface<T> {
        boolean hasNext();
        T next();
    }
    
    // Aggregate interface
    interface IterableCollection<T> {
        IteratorInterface<T> createIterator();
    }
    
    // Concrete Aggregate
    static class NameCollection implements IterableCollection<String> {
        private String[] names;
        public NameCollection(String[] names) { this.names = names; }
        
        @Override
        public IteratorInterface<String> createIterator() {
            return new NameIterator();
        }
        
        private class NameIterator implements IteratorInterface<String> {
            private int index = 0;
            @Override
            public boolean hasNext() { return index < names.length; }
            @Override
            public String next() { return names[index++]; }
        }
    }
    
    public static void demonstrate() {
        System.out.println("=== Iterator Pattern ===");
        System.out.println("Provides sequential access to elements without exposing structure.\n");
        
        String[] names = {"Alice", "Bob", "Charlie", "Diana"};
        NameCollection collection = new NameCollection(names);
        IteratorInterface<String> iterator = collection.createIterator();
        
        System.out.println("Iterating through names:");
        while (iterator.hasNext()) {
            System.out.println("  " + iterator.next());
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
