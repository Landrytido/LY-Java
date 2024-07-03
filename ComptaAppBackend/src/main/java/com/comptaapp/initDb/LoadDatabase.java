package com.comptaapp.initDb;

import com.comptaapp.model.Role;
import com.comptaapp.model.User;
import com.comptaapp.repository.RoleRepository;
import com.comptaapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Initialize roles
            List<String> roles = Arrays.asList("superadmin", "admin", "employee", "intern");
            for (String roleName : roles) {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            }

            // Initialize root user
            User rootUser = userRepository.findByUsername("root");
            if (rootUser == null) {
                User user = new User();
                user.setUsername("root");
                user.setEmail("root@localhost");
                user.setPassword(passwordEncoder.encode("root1234"));
                user.setEnabled(true);

                user = userRepository.save(user);

                // Fetch the role from the database to ensure it is managed
                Role role = roleRepository.findByName("superadmin");

                HashSet<Role> roles2 = new HashSet<>();
                roles2.add(roleRepository.findByName("superadmin"));

                user.setRoles(roles2);
                userRepository.save(user);
            }
        };
    }
}
