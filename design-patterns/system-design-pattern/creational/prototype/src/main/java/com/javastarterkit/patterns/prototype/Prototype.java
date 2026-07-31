package com.javastarterkit.patterns.prototype;

/**
 * Prototype Pattern Example
 * 
 * Creates new objects by copying existing objects (prototypes).
 * Like cloning a document template to create new documents.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class Prototype {
    
    public static void demonstrate() {
        System.out.println("\n=== Prototype Pattern ===");
        System.out.println("Creates objects by cloning prototypes\n");
        
        // Create prototype
        DocumentTemplate template = new DocumentTemplate();
        template.setTitle("Project Proposal");
        template.setContent("This is a template for project proposals.");
        template.addAuthor("Admin");
        
        // Clone template to create new documents
        System.out.println("Creating document 1 from template:");
        DocumentTemplate doc1 = template.clone();
        doc1.setTitle("Project Proposal - Client A");
        doc1.addAuthor("John");
        System.out.println("  " + doc1);
        
        System.out.println("\nCreating document 2 from template:");
        DocumentTemplate doc2 = template.clone();
        doc2.setTitle("Project Proposal - Client B");
        doc2.addAuthor("Jane");
        System.out.println("  " + doc2);
        
        System.out.println("\nOriginal template:");
        System.out.println("  " + template);
        
        System.out.println("\nBenefits:");
        System.out.println("- Avoids creating new objects from scratch");
        System.out.println("- Faster object creation");
        System.out.println("- Useful when initialization is expensive");
    }
    
    // Prototype interface
    interface Document extends Cloneable {
        Document clone();
        String getTitle();
        void setTitle(String title);
    }
    
    // Concrete prototype
    static class DocumentTemplate implements Document {
        private String title;
        private String content;
        private java.util.List<String> authors = new java.util.ArrayList<>();
        
        @Override
        public Document clone() {
            try {
                DocumentTemplate cloned = (DocumentTemplate) super.clone();
                // Deep copy of mutable fields
                cloned.authors = new java.util.ArrayList<>(this.authors);
                cloned.content = this.content;
                return cloned;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public void addAuthor(String author) { authors.add(author); }
        public java.util.List<String> getAuthors() { return authors; }
        
        public String toString() {
            return "Document{title='" + title + "', authors=" + authors + "}";
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}