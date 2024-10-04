package com.example.hql;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/VampireWars.ttf"), 55);

        // Create a Text node with the custom font
        Text text = new Text("HQL");
        text.setFont(customFont);

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        BorderPane root = fxmlLoader.load();

        // Set the Text node to the top of the BorderPane

        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
