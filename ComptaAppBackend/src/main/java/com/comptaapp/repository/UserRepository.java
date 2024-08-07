package com.comptaapp.repository;

import com.comptaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByIsEnabled(boolean isEnabled);
}
