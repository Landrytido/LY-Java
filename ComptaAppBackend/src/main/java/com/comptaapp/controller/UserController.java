package com.comptaapp.controller;

import com.comptaapp.dto.UserApprovalDto;
import com.comptaapp.dto.UserRegistrationDto;
import com.comptaapp.model.Role;
import com.comptaapp.model.User;
import com.comptaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }



    @GetMapping("/users/pending")
    public ResponseEntity<List<User>> getPendingUsers() {
        List<User> pendingUsers = userService.findPendingUsers();
        return ResponseEntity.ok(pendingUsers);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User newUser = userService.registerUser(registrationDto);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/activate/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        User user = userService.activateUser(userId);
        if (user != null) {
            return ResponseEntity.ok("User activated successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users/approve")
    public ResponseEntity<?> approveUser(@RequestBody UserApprovalDto userApprovalDto) {
        User user = userService.findByUsername(userApprovalDto.getUsername());
        Role role = userService.findRoleByName(userApprovalDto.getRoleName());

        if (user != null && role != null) {
            user.setRoles(new HashSet<>(Collections.singleton(role)));
            userService.approveUser(user);
            return ResponseEntity.ok("User approved successfully.");
        }

        return ResponseEntity.badRequest().body("User or role not found.");
    }

    @PostMapping("/users/updateRole")
    public ResponseEntity<?> updateUserRole(@RequestBody User user) {
        userService.updateUserRole(user);
        return ResponseEntity.ok("User role updated successfully.");
    }



    @PostConstruct
    public void init() {
        System.out.println("UserController initialized");
    }

}
