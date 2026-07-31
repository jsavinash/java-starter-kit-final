package com.javastarterkit.patterns.builder;

/**
 * Builder Pattern Example
 * 
 * Constructs complex objects step by step, allowing different representations.
 * Like building a house with different options for each part.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Builder {
    
    public static void demonstrate() {
        System.out.println("\n=== Builder Pattern ===");
        System.out.println("Constructs complex objects step by step\n");
        
        // Build a simple house
        House simpleHouse = new House.HouseBuilder()
            .buildFoundation("Concrete")
            .buildStructure("Wood")
            .buildRoof("Shingles")
            .build();
        
        System.out.println("Simple House: " + simpleHouse);
        
        // Build a luxury house
        House luxuryHouse = new House.HouseBuilder()
            .buildFoundation("Reinforced Concrete")
            .buildStructure("Steel and Glass")
            .buildRoof("Slate")
            .buildInterior("Premium")
            .buildGarage("2-Car")
            .build();
        
        System.out.println("Luxury House: " + luxuryHouse);
        
        System.out.println("\nBenefits:");
        System.out.println("- Constructs complex objects step by step");
        System.out.println("- Allows different representations");
        System.out.println("- Improves code readability");
    }
    
    // Product class
    static class House {
        private String foundation;
        private String structure;
        private String roof;
        private String interior;
        private String garage;
        
        private House() {}
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("House [");
            sb.append("Foundation: ").append(foundation).append(", ");
            sb.append("Structure: ").append(structure).append(", ");
            sb.append("Roof: ").append(roof);
            if (interior != null) {
                sb.append(", Interior: ").append(interior);
            }
            if (garage != null) {
                sb.append(", Garage: ").append(garage);
            }
            sb.append("]");
            return sb.toString();
        }
        
        // Builder class
        static class HouseBuilder {
            private House house = new House();
            
            public HouseBuilder buildFoundation(String foundation) {
                house.foundation = foundation;
                return this;
            }
            
            public HouseBuilder buildStructure(String structure) {
                house.structure = structure;
                return this;
            }
            
            public HouseBuilder buildRoof(String roof) {
                house.roof = roof;
                return this;
            }
            
            public HouseBuilder buildInterior(String interior) {
                house.interior = interior;
                return this;
            }
            
            public HouseBuilder buildGarage(String garage) {
                house.garage = garage;
                return this;
            }
            
            public House build() {
                return house;
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}