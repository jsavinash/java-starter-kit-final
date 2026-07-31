package com.javastarterkit.patterns.composite;

/**
 * Composite Pattern Example
 * 
 * Composes objects into tree structures to represent part-whole hierarchies.
 * Like a file system with files and directories.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Composite {
    
    public static void demonstrate() {
        System.out.println("\n=== Composite Pattern ===");
        System.out.println("Composes objects into tree structures\n");
        
        // Create file system structure
        FileComponent root = new Directory("root");
        FileComponent docs = new Directory("documents");
        FileComponent photos = new Directory("photos");
        
        root.add(docs);
        root.add(photos);
        
        docs.add(new File("resume.pdf"));
        docs.add(new File("cover_letter.pdf"));
        
        photos.add(new File("vacation.jpg"));
        photos.add(new File("family.png"));
        
        System.out.println("File System Structure:");
        root.display(0);
        
        System.out.println("\nTotal files: " + root.countFiles());
        
        System.out.println("\nBenefits:");
        System.out.println("- Treats individual and composite objects uniformly");
        System.out.println("- Makes it easy to add new component types");
        System.out.println("- Simplifies client code");
    }
    
    // Component interface
    interface FileComponent {
        void display(int indent);
        int countFiles();
        String getName();
    }
    
    // Leaf
    static class File implements FileComponent {
        private String name;
        
        public File(String name) {
            this.name = name;
        }
        
        @Override
        public void display(int indent) {
            for (int i = 0; i < indent; i++) {
                System.out.print("  ");
            }
            System.out.println("- " + name);
        }
        
        @Override
        public int countFiles() {
            return 1;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
    
    // Composite
    static class Directory implements FileComponent {
        private String name;
        private java.util.List<FileComponent> children = new java.util.ArrayList<>();
        
        public Directory(String name) {
            this.name = name;
        }
        
        public void add(FileComponent component) {
            children.add(component);
        }
        
        public void remove(FileComponent component) {
            children.remove(component);
        }
        
        @Override
        public void display(int indent) {
            for (int i = 0; i < indent; i++) {
                System.out.print("  ");
            }
            System.out.println("[DIR] " + name + "/");
            for (FileComponent child : children) {
                child.display(indent + 1);
            }
        }
        
        @Override
        public int countFiles() {
            int total = 0;
            for (FileComponent child : children) {
                total += child.countFiles();
            }
            return total;
        }
        
        @Override
        public String getName() {
            return name;
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}