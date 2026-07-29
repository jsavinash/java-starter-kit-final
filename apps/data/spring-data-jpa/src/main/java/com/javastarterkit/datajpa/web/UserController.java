// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.datajpa.web;

import com.javastarterkit.datajpa.entity.User;
import com.javastarterkit.datajpa.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller demonstrating Spring Data JPA CRUD operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @SuppressFBWarnings(value = "EI2", justification = "UserService is managed by Spring and effectively immutable")
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User createdUser = userService.createUser(user);
        response.put("user", createdUser);
        response.put("message", "User created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.getUserById(id);
        if (user != null) {
            response.put("user", user);
            return ResponseEntity.ok(response);
        }
        response.put("error", "User not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<User> users = userService.getAllUsers();
        response.put("users", users);
        response.put("count", users.size());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Map<String, Object> response = new HashMap<>();
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            response.put("user", updatedUser);
            response.put("message", "User updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "User not found with id: " + id);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        userService.deleteUser(id);
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        List<User> users = userService.searchUsersByUsername(username);
        response.put("users", users);
        response.put("count", users.size());
        return ResponseEntity.ok(response);
    }
}
