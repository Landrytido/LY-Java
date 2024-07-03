package com.comptaapp.comptaappfrontend.controller;

import com.comptaapp.comptaappfrontend.model.User;
import com.comptaapp.comptaappfrontend.service.UserService;
import com.comptaapp.comptaappfrontend.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UserController {

    @FXML
    private TextField usernameField;

    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @FXML
    private void handleSearchUser() {
        String username = usernameField.getText();
        try {
            User user = userService.findUserByUsername(username);  // Correct method name

            if (user != null) {
                AlertUtil.showAlert("User Found", "User: " + user.getUsername() + "\nEmail: " + user.getEmail());
            } else {
                AlertUtil.showAlert("User Not Found", "No user found with username: " + username);
            }
        } catch (IOException e) {
            AlertUtil.showAlert("Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
