package com.javastarterkit.patterns.solid.singleresponsibility.SingleResponsibility.java;

/**
 * Single Responsibility Principle Pattern
 * 
 * System design pattern example demonstrating the Single Responsibility Principle pattern.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class SingleResponsibility {
    
    public static void demonstrate() {

        // Class with single responsibility: hold email content
        static class EmailContent {
            private String to;
            private String subject;
            private String body;
            
            public EmailContent(String to, String subject, String body) {
                this.to = to; this.subject = subject; this.body = body;
            }
            
            public String getTo() { return to; }
            public String getSubject() { return subject; }
            public String getBody() { return body; }
        }
        
        // Class with single responsibility: send emails
        static class EmailSender {
            public void send(EmailContent content) {
                System.out.println("  Sending email to: " + content.getTo());
                System.out.println("  Subject: " + content.getSubject());
                System.out.println("  Body: " + content.getBody());
                System.out.println("  Email sent successfully!");
            }
        }
        
        System.out.println("=== Single Responsibility Principle ===");
        System.out.println("A class should have only one reason to change.\n");
        
        EmailContent content = new EmailContent("user@example.com", "Hello", "This is a test email");
        EmailSender sender = new EmailSender();
        sender.send(content);
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}
