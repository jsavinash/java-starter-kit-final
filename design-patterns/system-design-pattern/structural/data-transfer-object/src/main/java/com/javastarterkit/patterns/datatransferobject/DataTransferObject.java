package com.javastarterkit.patterns.datatransferobject;

/**
 * Data Transfer Object (DTO) Pattern Example
 * 
 * Transfers data between application layers or across network boundaries.
 * Like an API request/response object containing multiple fields.
 * 
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class DataTransferObject {
    
    public static void demonstrate() {
        System.out.println("\n=== Data Transfer Object Pattern ===");
        System.out.println("Transfers data between layers\n");
        
        // Create user DTO
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        
        // Transfer from client to server
        System.out.println("Client sending user data:");
        System.out.println("  " + user);
        
        // Server processes and returns response
        System.out.println("\nServer processing...");
        ResponseDTO response = new ResponseDTO();
        response.setSuccess(true);
        response.setMessage("User created successfully");
        response.setData(user);
        
        System.out.println("Server sending response:");
        System.out.println("  Success: " + response.isSuccess());
        System.out.println("  Message: " + response.getMessage());
        System.out.println("  Data: " + response.getData());
        
        System.out.println("\nBenefits:");
        System.out.println("- Reduces number of method calls");
        System.out.println("- Serializes data for network transfer");
        System.out.println("- Separates internal from external data models");
    }
    
    // DTO class
    static class UserDTO {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private int age;
        
        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        
        public String toString() {
            return "UserDTO{id=" + id + ", name='" + firstName + " " + lastName + 
                   "', email='" + email + "', age=" + age + "}";
        }
    }
    
    // Response DTO
    static class ResponseDTO {
        private boolean success;
        private String message;
        private UserDTO data;
        
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public UserDTO getData() { return data; }
        public void setData(UserDTO data) { this.data = data; }
    }
    
    public static void main(String[] args) {
        demonstrate();
    }
}