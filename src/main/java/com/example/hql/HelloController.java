package com.example.hql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class HelloController {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, Integer> timeRemainingColumn;

    @FXML
    private TableColumn<User, Double> balanceColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField topupField;

    @FXML
    private TextField topupAmountField;

    private ObservableList<User> userList;

    private User selectedUser;

    @FXML
    public void initialize() {
        // Initialize the user list and bind it to the TableView
        userList = FXCollections.observableArrayList();
        userTableView.setItems(userList);

        // Bind table columns to the User properties
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        timeRemainingColumn.setCellValueFactory(new PropertyValueFactory<>("timeRemaining"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Set up listener for selecting a user
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedUser = newValue; // The user that is selected in the table
        });
    }

    @FXML
    protected void onCreateAccountClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String topupAmountText = topupField.getText();

        if (!username.isEmpty() && !password.isEmpty() && !topupAmountText.isEmpty()) {
            try {
                double topupAmount = Double.parseDouble(topupAmountText);
                int hours = (int) (topupAmount / 20); // Assuming 20 currency units give 1 hour of time

                // Create a new user account and add it to the table
                User newUser = new User(username, hours, topupAmount);
                userList.add(newUser);

                // Clear the fields after account creation
                usernameField.clear();
                passwordField.clear();
                topupField.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid top-up amount. Please enter a numeric value.");
            }
        } else {
            System.out.println("Please fill in all fields.");
        }
    }

    @FXML
    protected void onTopUpClick() {
        if (selectedUser != null) {
            String topupAmountText = topupAmountField.getText();

            try {
                double topupAmount = Double.parseDouble(topupAmountText);
                int additionalHours = (int) (topupAmount / 20);

                // Update the selected user's time and balance
                selectedUser.setTimeRemaining(selectedUser.getTimeRemaining() + additionalHours);
                selectedUser.setBalance(selectedUser.getBalance() + topupAmount);

                // Refresh the TableView to reflect updated user data
                userTableView.refresh();

                // Clear the top-up field after updating
                topupAmountField.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid top-up amount. Please enter a numeric value.");
            }
        } else {
            System.out.println("No user selected. Please select a user to top-up.");
        }
    }
}
