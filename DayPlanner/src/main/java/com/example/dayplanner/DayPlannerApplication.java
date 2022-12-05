package com.example.dayplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DayPlannerApplication extends Application {

    /*
    * Before loading any view we check if in directory already exist file users.csv.
    * If this file doesn't exist we load registration-view otherwise we load login-view.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        File users = new File("users.csv");
        if(users.exists()){
            fxmlLoader = new FXMLLoader(DayPlannerApplication.class.getResource("login-view.fxml"));
        }else {
            fxmlLoader = new FXMLLoader(DayPlannerApplication.class.getResource("registration-view.fxml"));
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