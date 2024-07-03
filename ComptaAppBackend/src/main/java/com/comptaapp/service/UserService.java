package com.comptaapp.service;

import com.comptaapp.dto.UserRegistrationDto;
import com.comptaapp.model.Role;
import com.comptaapp.model.User;
import com.comptaapp.repository.RoleRepository;
import com.comptaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role role = roleRepository.findByName("intern");
        if (role == null) {
            role = new Role();
            role.setName("intern");
            role = roleRepository.save(role);
        }
        user.setRoles(new HashSet<>(Collections.singleton(role)));
        user.setEnabled(false); // Assurez-vous que l'utilisateur n'est pas activé par défaut

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findPendingUsers() {
        return userRepository.findByIsEnabled(false);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User activateUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
        }
        return user;
    }

    @Transactional
    public void approveUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRole(User user) {
        userRepository.save(user);
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
