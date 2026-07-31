package com.javastarterkit.patterns.flyweight;

/**
 * Flyweight Pattern Example
 * 
 * Shares common state between objects to reduce memory usage.
 * Like a text editor sharing character formatting objects.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Flyweight {
    
    public static void demonstrate() {
        System.out.println("\n=== Flyweight Pattern ===");
        System.out.println("Shares common state to reduce memory usage\n");
        
        // Create flyweight factory
        CharacterFactory factory = new CharacterFactory();
        
        // Use characters with different positions but same font/style
        System.out.println("Creating characters for text: 'Hello World'");
        
        char[][] textPositions = {
            {'H', 0, 0}, {'e', 1, 0}, {'l', 2, 0}, {'l', 3, 0}, {'o', 4, 0},
            {' ', 5, 0}, {'W', 6, 0}, {'o', 7, 0}, {'r', 8, 0}, {'l', 9, 0}, {'d', 10, 0}
        };
        
        int memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        
        for (char[] pos : textPositions) {
            char ch = pos[0];
            int x = pos[1];
            int y = pos[2];
            
            // Get flyweight (shared) character object
            Character character = factory.getCharacter(ch);
            character.display(x, y);
        }
        
        int memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        
        System.out.println("\nTotal unique character objects created: " + factory.getCount());
        System.out.println("Total characters in text: " + textPositions.length);
        System.out.println("Memory saved: " + (textPositions.length - factory.getCount()) + " objects");
        
        System.out.println("\nBenefits:");
        System.out.println("- Reduces memory usage by sharing objects");
        System.out.println("- Improves performance for large datasets");
        System.out.println("- Useful for text editors, games, simulations");
    }
    
    // Flyweight interface
    interface Character {
        void display(int x, int y);
    }
    
    // Concrete Flyweight
    static class CharacterObject implements Character {
        private char character;
        private String font;
        private int size;
        
        public CharacterObject(char character, String font, int size) {
            this.character = character;
            this.font = font;
            this.size = size;
        }
        
        @Override
        public void display(int x, int y) {
            System.out.println("  Displaying '" + character + "' at (" + x + ", " + y + 
                             ") with font " + font + " size " + size);
        }
    }
    
    // Flyweight Factory
    static class CharacterFactory {
        private java.util.Map<Character, CharacterObject> cache = new java.util.HashMap<>();
        private int count = 0;
        
        public Character getCharacter(char c) {
            if (!cache.containsKey(c)) {
                cache.put(c, new CharacterObject(c, "Arial", 12));
                count++;
            }
            return cache.get(c);
        }
        
        public int getCount() {
            return count;
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}