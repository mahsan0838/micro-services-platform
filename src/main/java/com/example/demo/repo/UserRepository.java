package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsById(long id);
    List<User> findByEmailIn(List<String> emails);
    // no custom method needed as of right now
}