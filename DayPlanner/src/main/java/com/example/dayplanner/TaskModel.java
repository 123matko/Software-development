package com.example.dayplanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TaskModel extends Pane {

    public TaskModel(String time,String name,boolean done,boolean isActive) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskModel.fxml"));
        TaskController taskController = new TaskController(time,name,done,isActive);
        fxmlLoader.setController(taskController);
        Node view = (Node) fxmlLoader.load();
        getChildren().add(view);
    }
}
