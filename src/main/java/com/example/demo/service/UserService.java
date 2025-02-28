package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> registerUser(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(long id) {
        return userRepository.existsById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<String> getExistingEmails(List<String> emails) {
        List<User> users = userRepository.findByEmailIn(emails);
        if (users == null || users.isEmpty()) {
            return List.of(); // Return an empty list instead of null
        }
        return users.stream().map(User::getEmail).toList();
    }

}
