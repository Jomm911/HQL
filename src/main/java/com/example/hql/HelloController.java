package com.example.hql;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HelloController {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> timeRemainingColumn; // Change to String

    @FXML
    private TableColumn<User, Double> balanceColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField amount;

    @FXML
    private TextField topupAmountField;

    @FXML
    private TextField search; // For the search functionality

    private ObservableList<User> userList;

    private User selectedUser;

    @FXML
    public void initialize() {
        // Initialize the ObservableList to hold users
        userList = FXCollections.observableArrayList();
        userTableView.setItems(userList); // Set the user list to the TableView

        // Bind the username column to the "username" property
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Bind timeRemainingColumn to the formatted time string
        timeRemainingColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleStringProperty(user.getFormattedTimeRemaining()); // Format the time remaining as a string
        });

        // Bind balance column to the "balance" property
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Add listener to update selectedUser when a user is selected from the TableView
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedUser = newValue; // Update the selected user
            if (selectedUser != null) {
                System.out.println("Selected user: " + selectedUser.getUsername()); // Optional: For debugging
            }
        });

        // Add listener to filter users dynamically when search input changes
        search.textProperty().addListener((observable, oldValue, newValue) -> filterUserList(newValue));
    }


    // Method to filter user list based on search input
    private void filterUserList(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            userTableView.setItems(userList); // Show all users if search is empty
        } else {
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (User user : userList) {
                if (user.getUsername().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredList.add(user);
                }
            }
            userTableView.setItems(filteredList);
        }
    }

    @FXML
    protected void onCreateAccountClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String topupAmountText = amount.getText();

        if (!username.isEmpty() && !password.isEmpty() && !topupAmountText.isEmpty()) {
            try {
                double topupAmount = Double.parseDouble(topupAmountText);
                int totalMinutes = (int) (topupAmount / 20) * 60; // Convert full hours to minutes
                int remainingPesos = (int) (topupAmount % 20); // Calculate remaining pesos
                int remainingMinutes = (remainingPesos > 0) ? (remainingPesos * 3) : 0; // Convert remaining pesos to minutes (20 pesos = 60 mins, so 1 peso = 3 mins)

                totalMinutes += remainingMinutes; // Add the remaining minutes

                System.out.println("Creating user: " + username + " with " + totalMinutes + " total minutes.");

                // Create a new user account and add it to the table
                User newUser = new User(username, totalMinutes, topupAmount);
                userList.add(newUser);

                usernameField.clear();
                passwordField.clear();
                amount.clear();
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
                int additionalMinutes = (int) (topupAmount / 20) * 60;
                int remainingPesos = (int) (topupAmount % 20);
                int remainingMinutes = (remainingPesos > 0) ? (remainingPesos * 3) : 0;

                additionalMinutes += remainingMinutes;

                // Update the selected user's time and balance
                selectedUser.setTimeRemaining(selectedUser.getTimeRemaining() + additionalMinutes);
                selectedUser.setBalance(selectedUser.getBalance() + topupAmount);

                userTableView.refresh();
                topupAmountField.clear();

                System.out.println("Updated user: " + selectedUser.getUsername() +
                        ", new time remaining: " + selectedUser.getFormattedTimeRemaining() +
                        ", new balance: ₱" + selectedUser.getBalance());
            } catch (NumberFormatException e) {
                System.out.println("Invalid top-up amount. Please enter a numeric value.");
            }
        } else {
            System.out.println("No user selected. Please select a user to top-up.");
        }

    }



}
