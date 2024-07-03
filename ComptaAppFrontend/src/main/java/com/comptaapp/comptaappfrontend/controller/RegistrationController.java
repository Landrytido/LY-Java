package com.comptaapp.comptaappfrontend.controller;

import com.comptaapp.comptaappfrontend.model.User;
import com.comptaapp.comptaappfrontend.service.UserService;
import com.comptaapp.comptaappfrontend.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UserService userService;
    private Stage primaryStage;

    public RegistrationController() {
        this.userService = new UserService();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = new User(username, email, password);
        boolean isRegistered;
        try {
            isRegistered = userService.registerUser(user);
            if (isRegistered) {
                AlertUtil.showAlert("Registration Successful", "User " + username + " has been registered.");
                navigateToLogin();
            } else {
                AlertUtil.showAlert("Registration Failed", "User registration failed.");
            }
        } catch (IOException e) {
            AlertUtil.showAlert("Registration Failed", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginNavigation() {
        navigateToLogin();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/comptaapp/comptaappfrontend/fxml/login-view.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert("Navigation Error", "Failed to load the login page.");
        }
    }
}
