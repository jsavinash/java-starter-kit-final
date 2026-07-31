package com.javastarterkit.patterns.chainofresponsibility;

/**
 * Chain of Responsibility Pattern Example
 * 
 * Passes requests along a chain of handlers, each deciding to process or forward.
 * Like a customer support ticket system escalating through levels.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class ChainOfResponsibility {
    
    public static void demonstrate() {
        System.out.println("\n=== Chain of Responsibility Pattern ===");
        System.out.println("Passes requests through handler chain\n");
        
        // Create handler chain
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new ManagerSupport();
        
        level1.setNextHandler(level2);
        level2.setNextHandler(level3);
        
        // Process support tickets
        System.out.println("Processing tickets:");
        level1.handleTicket("password_reset");
        level1.handleTicket("billing_issue");
        level1.handleTicket("legal_compliance");
        level1.handleTicket("unknown_issue");
        
        System.out.println("\nBenefits:");
        System.out.println("- Decouples sender and receiver");
        System.out.println("- Adds flexibility in assigning responsibilities");
        System.out.println("- Easy to add/remove handlers");
    }
    
    // Handler interface
    interface SupportHandler {
        void handleTicket(String issue);
        void setNextHandler(SupportHandler next);
    }
    
    // Abstract handler
    abstract static class AbstractSupportHandler implements SupportHandler {
        private SupportHandler next;
        
        @Override
        public void setNextHandler(SupportHandler next) {
            this.next = next;
        }
        
        protected void passToNext(String issue) {
            if (next != null) {
                next.handleTicket(issue);
            } else {
                System.out.println("  Ticket unassigned: " + issue);
            }
        }
    }
    
    // Concrete handlers
    static class Level1Support extends AbstractSupportHandler {
        @Override
        public void handleTicket(String issue) {
            if (issue.equals("password_reset")) {
                System.out.println("  Level 1: Password reset processed");
            } else {
                passToNext(issue);
            }
        }
    }
    
    static class Level2Support extends AbstractSupportHandler {
        @Override
        public void handleTicket(String issue) {
            if (issue.equals("billing_issue")) {
                System.out.println("  Level 2: Billing issue resolved");
            } else {
                passToNext(issue);
            }
        }
    }
    
    static class ManagerSupport extends AbstractSupportHandler {
        @Override
        public void handleTicket(String issue) {
            if (issue.equals("legal_compliance")) {
                System.out.println("  Manager: Escalated to legal team");
            } else {
                passToNext(issue);
            }
        }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}