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
        userList = FXCollections.observableArrayList();
        userTableView.setItems(userList);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Bind timeRemainingColumn to the formatted time string
        timeRemainingColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new SimpleStringProperty(user.getFormattedTimeRemaining());
        });

        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedUser = newValue;
        });

        // Add listener to the search field to filter users dynamically
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterUserList(newValue);
            }
        });
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
        if (selectedUser != null) { // Check if a user is selected
            String topupAmountText = topupAmountField.getText();

            try {
                // Parse the top-up amount entered by the user
                double topupAmount = Double.parseDouble(topupAmountText);

                // Calculate additional minutes and balance based on top-up
                int additionalMinutes = (int) (topupAmount / 20) * 60; // Full hours to minutes (20 pesos = 60 mins)
                int remainingPesos = (int) (topupAmount % 20); // Calculate remaining pesos
                int remainingMinutes = (remainingPesos > 0) ? (remainingPesos * 3) : 0; // Convert remaining pesos to minutes

                additionalMinutes += remainingMinutes; // Add the remaining minutes

                // Update the selected user's time and balance
                selectedUser.setTimeRemaining(selectedUser.getTimeRemaining() + additionalMinutes);
                selectedUser.setBalance(selectedUser.getBalance() + topupAmount);

                System.out.println("Updated user: " + selectedUser.getUsername() +
                        ", new time remaining: " + selectedUser.getTimeRemaining() + " minutes, balance: ₱" + selectedUser.getBalance());

                // Refresh the table to reflect updated time and balance
                userTableView.refresh();

                // Clear the top-up amount field
                topupAmountField.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid top-up amount. Please enter a numeric value.");
            }
        } else {
            System.out.println("No user selected. Please select a user to top-up.");
        }
    }


}
