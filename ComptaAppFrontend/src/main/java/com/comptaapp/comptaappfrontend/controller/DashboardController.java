package com.comptaapp.comptaappfrontend.controller;

import com.comptaapp.comptaappfrontend.model.Role;
import com.comptaapp.comptaappfrontend.model.User;
import com.comptaapp.comptaappfrontend.service.RoleService;
import com.comptaapp.comptaappfrontend.service.UserService;
import com.comptaapp.comptaappfrontend.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, Boolean> isEnabledColumn;

    @FXML
    private VBox pendingUsersSection;

    @FXML
    private Button approveButton;

    @FXML
    private Button assignRoleButton;

    @FXML
    private TextField roleField;

    @FXML
    private Button taxesButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button inventoryButton;

    @FXML
    private Button payrollButton;

    @FXML
    private Button invoicesButton;

    @FXML
    private Button accountsButton;

    @FXML
    private Button backupButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> roleComboBox;

    private UserService userService;
    private RoleService roleService;
    private Stage primaryStage;
    private User currentUser;

    public DashboardController() {
        this.userService = new UserService();
        this.roleService = new RoleService();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {

        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        isEnabledColumn.setCellValueFactory(cellData -> cellData.getValue().enabledProperty().asObject());

        usersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));

        loadAllUsers();
        loadAllRoles();
    }

    private void showUserDetails(User user) {
        if (user != null) {
            roleField.setText(user.getRole());
        } else {
            roleField.setText("");
        }
    }

    public void initialize(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        roleLabel.setText("Role: " + user.getRole());

        if ("superadmin".equals(user.getRole())) {
            loadAllUsers();
        } else {
            pendingUsersSection.setVisible(false);
        }

        setTaskVisibility(user.getRole());
    }

    private void loadAllRoles() {
        try {
            List<Role> allRoles = roleService.getAllRoles();
            ObservableList<String> observableAllRoles = FXCollections.observableArrayList();
            for (Role role : allRoles) {
                observableAllRoles.add(role.getName());
            }
            roleComboBox.setItems(observableAllRoles);

            if (allRoles.isEmpty()) {
                AlertUtil.showAlert("Info", "No roles found.");
            }
        } catch (IOException e) {
            AlertUtil.showAlert("Error", "Unable to load roles.");
            e.printStackTrace();
        }
    }

    private void loadAllUsers() {
        try {
            List<User> allUsers = userService.getAllUsers();  // Use mock users method
            ObservableList<User> observableAllUsers = FXCollections.observableArrayList(allUsers);
            usersTable.setItems(observableAllUsers);

            if (allUsers.isEmpty()) {
                AlertUtil.showAlert("Info", "No users found.");
            }
        } catch (IOException e) {
            AlertUtil.showAlert("Error", "Unable to load users.");
            e.printStackTrace();
        }
    }

    private void setTaskVisibility(String role) {
        taxesButton.setVisible("superadmin".equals(role) || "administrator".equals(role));
        reportsButton.setVisible("superadmin".equals(role) || "administrator".equals(role));
        inventoryButton.setVisible("superadmin".equals(role) || "employee".equals(role));
        payrollButton.setVisible("superadmin".equals(role) || "employee".equals(role));
        invoicesButton.setVisible("superadmin".equals(role) || "administrator".equals(role));
        accountsButton.setVisible("superadmin".equals(role) || "administrator".equals(role));
        backupButton.setVisible("superadmin".equals(role));
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("You have been logged out.");
        alert.showAndWait();
        // Implement navigation back to login view
    }

    @FXML
    private void handleApproveUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        String selectedRole = roleComboBox.getValue();
        if (selectedUser != null && selectedRole != null) {
            try {
                userService.approveUser(selectedUser.getUsername(), selectedRole);
                loadAllUsers();
                AlertUtil.showAlert("Success", "User approved.");
            } catch (IOException e) {
                AlertUtil.showAlert("Error", "Unable to approve user.");
                e.printStackTrace();
            }
        } else {
            if (selectedUser == null) {
                AlertUtil.showAlert("Error", "No user selected.");
            }
            if (selectedRole == null) {
                AlertUtil.showAlert("Error", "No role selected.");
            }
        }
    }

    @FXML
    private void handleSearchUser() {
        String searchQuery = searchField.getText();
        // Implement search logic here
        AlertUtil.showAlert("Search", "Searching for user: " + searchQuery);
    }

    @FXML
    private void handleTaxes() {
        AlertUtil.showAlert("Task", "Manage Taxes");
    }

    @FXML
    private void handleReports() {
        AlertUtil.showAlert("Task", "Financial Reports and Analysis");
    }

    @FXML
    private void handleInventory() {
        AlertUtil.showAlert("Task", "Manage Inventory");
    }

    @FXML
    private void handlePayroll() {
        AlertUtil.showAlert("Task", "Manage Payroll");
    }

    @FXML
    private void handleInvoices() {
        AlertUtil.showAlert("Task", "Manage Invoices and Payments");
    }

    @FXML
    private void handleAccounts() {
        AlertUtil.showAlert("Task", "Manage Accounts");
    }

    @FXML
    private void handleBackup() {
        AlertUtil.showAlert("Task", "Backup Data");
    }
}
