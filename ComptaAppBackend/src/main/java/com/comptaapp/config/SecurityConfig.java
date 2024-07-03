package com.comptaapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/register", "/login").permitAll()  // Allow everyone to access register and login
                        .antMatchers("/vertretungsplan").hasAnyRole("SCHUELER", "LEHRER", "VERWALTUNG")  // Specific access controls
                        .anyRequest().permitAll())  // Require authentication for any other request
                .formLogin(form -> form
                        .loginPage("/login")  // Define the login page path
                        .defaultSuccessUrl("/dashboard", true))  // Redirect to the dashboard upon successful login
                .logout(logout -> logout
                        .logoutSuccessUrl("/login"));  // Define the logout success URL

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
