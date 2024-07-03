package com.comptaapp.comptaappfrontend.controller;

import com.comptaapp.comptaappfrontend.model.User;
import com.comptaapp.comptaappfrontend.service.AuthService;
import com.comptaapp.comptaappfrontend.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private AuthService authService;
    private Stage primaryStage;

    public LoginController() {
        this.authService = new AuthService();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = authService.authenticate(username, password);

        if (user != null) {
            AlertUtil.showAlert("Login Successful", "Welcome, " + username);
            navigateToDashboard(user);
        } else {
            AlertUtil.showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleRegisterNavigation() {
        navigateToRegister();
    }

    private void navigateToDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/comptaapp/comptaappfrontend/fxml/dashboard-view.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage);
            dashboardController.initialize(user);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert("Navigation Error", "Failed to load the dashboard.");
        }
    }

    private void navigateToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/comptaapp/comptaappfrontend/fxml/register-view.fxml"));
            Parent root = loader.load();

            RegistrationController registrationController = loader.getController();
            registrationController.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert("Navigation Error", "Failed to load the register page.");
        }
    }
}
