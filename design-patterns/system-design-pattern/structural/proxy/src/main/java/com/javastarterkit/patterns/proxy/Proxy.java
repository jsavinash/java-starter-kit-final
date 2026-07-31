package com.javastarterkit.patterns.proxy;

/**
 * Proxy Pattern Example
 * 
 * Controls access to an object, adding an extra layer of indirection.
 * Like a credit card as a proxy for cash in a wallet.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Proxy {
    
    public static void demonstrate() {
        System.out.println("\n=== Proxy Pattern ===");
        System.out.println("Controls access to an object with a surrogate\n");
        
        // Create a proxy for an expensive image
        Image image1 = new ImageProxy("photo1.jpg");
        Image image2 = new ImageProxy("photo2.jpg");
        Image image3 = new ImageProxy("photo3.jpg");
        
        System.out.println("Images created (not loaded yet)");
        
        System.out.println("\nDisplaying image1 (will load from disk):");
        image1.display();
        
        System.out.println("\nDisplaying image1 again (already loaded):");
        image1.display();
        
        System.out.println("\nDisplaying image2 (will load from disk):");
        image2.display();
        
        System.out.println("\nBenefits:");
        System.out.println("- Controls access to expensive resources");
        System.out.println("- Can implement lazy loading");
        System.out.println("- Adds security, caching, or logging");
    }
    
    // Subject interface
    interface Image {
        void display();
    }
    
    // Real Subject
    static class RealImage implements Image {
        private String filename;
        
        public RealImage(String filename) {
            this.filename = filename;
            loadFromDisk();
        }
        
        private void loadFromDisk() {
            System.out.println("  Loading image from disk: " + filename);
        }
        
        @Override
        public void display() {
            System.out.println("  Displaying image: " + filename);
        }
    }
    
    // Proxy
    static class ImageProxy implements Image {
        private String filename;
        private RealImage realImage;
        
        public ImageProxy(String filename) {
            this.filename = filename;
        }
        
        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            }
            realImage.display();
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}