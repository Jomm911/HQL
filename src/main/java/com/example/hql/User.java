package com.example.hql;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty username;
    private SimpleIntegerProperty timeRemaining; // in hours
    private SimpleDoubleProperty balance; // in currency

    public User(String username, int timeRemaining, double balance) {
        this.username = new SimpleStringProperty(username);
        this.timeRemaining = new SimpleIntegerProperty(timeRemaining);
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
}
