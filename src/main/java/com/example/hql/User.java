package com.example.hql;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty username;
    private SimpleIntegerProperty timeRemaining; // in total minutes
    private SimpleDoubleProperty balance; // in currency

    public User(String username, int timeRemaining, double balance) {
        this.username = new SimpleStringProperty(username);
        this.timeRemaining = new SimpleIntegerProperty(timeRemaining); // Set in minutes
        this.balance = new SimpleDoubleProperty(balance);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public int getTimeRemaining() {
        return timeRemaining.get();
    }

    public SimpleIntegerProperty timeRemainingProperty() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining.set(timeRemaining);
    }

    public double getBalance() {
        return balance.get();
    }

    public SimpleDoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    // Method to get formatted time remaining
    public String getFormattedTimeRemaining() {
        int hours = timeRemaining.get() / 60; // Convert total minutes to hours
        int minutes = timeRemaining.get() % 60; // Get remaining minutes
        return hours + "hr " + minutes + "mins"; // Format the string
    }
}