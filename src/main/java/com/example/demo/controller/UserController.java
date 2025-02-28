package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        }
        return ResponseEntity.ok(users); // 200 OK
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> checkUser(@PathVariable long id) {
        boolean exists = userService.userExists(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUsers(@RequestBody List<User> users) {
        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User list cannot be empty.");
        }

        // Extract emails from request
        List<String> emails = users.stream().map(User::getEmail).toList();

        // Check if any of these emails already exist
        List<String> existingEmails = userService.getExistingEmails(emails);

        if (!existingEmails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The following emails are already registered: " + existingEmails);
        }

        // Register if no duplicates
        List<User> registeredUsers = userService.registerUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUsers);
    }

}
