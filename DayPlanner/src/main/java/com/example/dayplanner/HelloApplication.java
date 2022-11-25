package com.example.dayplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;

        File users = new File("users.csv");


        if(users.exists()){
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        }else {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration-view.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("DayPlanner");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}