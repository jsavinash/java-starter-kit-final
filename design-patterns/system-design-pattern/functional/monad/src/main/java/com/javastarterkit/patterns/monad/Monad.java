package com.javastarterkit.patterns.monad;

/**
 * Monad Pattern
 * 
 * Wraps values and provides composition operations.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Monad {
    
    static class Maybe<T> {
        private final T value;
        private Maybe(T value) { this.value = value; }
        
        public static <T> Maybe<T> of(T value) { return new Maybe<>(value); }
        
        public <R> Maybe<R> flatMap(java.util.function.Function<T, Maybe<R>> mapper) {
            if (value == null) { return new Maybe<>(null); }
            return mapper.apply(value);
        }
        
        public <R> Maybe<R> map(java.util.function.Function<T, R> mapper) {
            if (value == null) { return new Maybe<>(null); }
            return new Maybe<>(mapper.apply(value));
        }
        
        public T getOrElse(T defaultValue) { return value != null ? value : defaultValue; }
    }
    
    public static void demonstrate() {
        System.out.println("=== Monad Pattern ===");
        System.out.println("Wraps values and provides composition operations.\n");
        
        Maybe<Integer> maybeValue = Maybe.of(5);
        String result = maybeValue.map(x -> x * 2).map(x -> "Value: " + x).getOrElse("No value");
        System.out.println("Result: " + result);
        
        Maybe<Integer> maybeNull = Maybe.of((Integer) null);
        String nullResult = maybeNull.map(x -> x * 2).map(x -> "Value: " + x).getOrElse("No value");
        System.out.println("Null result: " + nullResult);
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
