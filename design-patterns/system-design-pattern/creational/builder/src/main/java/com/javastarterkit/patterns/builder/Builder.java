package com.javastarterkit.patterns.builder;

/**
 * Builder Pattern
 * 
 * Constructs complex objects step by step.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Builder {
    
    static class House {
        private String foundation, structure, roof, interior;
        public void setFoundation(String f) { this.foundation = f; }
        public void setStructure(String s) { this.structure = s; }
        public void setRoof(String r) { this.roof = r; }
        public void setInterior(String i) { this.interior = i; }
        public void display() {
            System.out.println("House built with: " + foundation + ", " + structure + ", " + roof + ", " + interior);
        }
    }
    
    interface HouseBuilder {
        HouseBuilder buildFoundation(String foundation);
        HouseBuilder buildStructure(String structure);
        HouseBuilder buildRoof(String roof);
        HouseBuilder buildInterior(String interior);
        House build();
    }
    
    static class ConcreteHouseBuilder implements HouseBuilder {
        private House house = new House();
        public HouseBuilder buildFoundation(String f) { house.setFoundation(f); return this; }
        public HouseBuilder buildStructure(String s) { house.setStructure(s); return this; }
        public HouseBuilder buildRoof(String r) { house.setRoof(r); return this; }
        public HouseBuilder buildInterior(String i) { house.setInterior(i); return this; }
        public House build() { return house; }
    }
    
    public static void demonstrate() {
        System.out.println("=== Builder Pattern ===");
        System.out.println("Constructs complex objects step by step.\n");
        
        House house = new ConcreteHouseBuilder()
            .buildFoundation("Concrete Foundation")
            .buildStructure("Wood Frame")
            .buildRoof("Clay Tiles")
            .buildInterior("Modern Design")
            .build();
        house.display();
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
