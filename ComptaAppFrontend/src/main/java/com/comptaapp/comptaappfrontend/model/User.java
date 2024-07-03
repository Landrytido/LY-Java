package com.comptaapp.comptaappfrontend.model;

import javafx.beans.property.*;

import java.util.List;
import java.util.Set;

public class User {
    private final StringProperty username;
    private final StringProperty email;
    private final ObjectProperty<Set<Role>> roles = new SimpleObjectProperty<>();
    private final StringProperty role;
    private final StringProperty password;
    private final BooleanProperty enabled;

    // Constructor with all parameters
    public User(String username, String email, String role, String password, boolean enabled) {
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.password = new SimpleStringProperty(password);
        this.enabled = new SimpleBooleanProperty(enabled);
    }

    // Constructor without role and enabled status (for registration)
    public User(String username, String email, String password) {
        this(username, email, "", password, false);
    }

    // Getters and setters
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getRole() {
        //return first role in the set
        return roles.get().iterator().next().getName();
    }

    public Set<Role> getRoles() {
        return roles.get();
    }

    public void setRoles(Set<Role> roles) {
        this.roles.set(roles);
    }

    public ObjectProperty<Set<Role>> rolesProperty() {
        return roles;
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

}
