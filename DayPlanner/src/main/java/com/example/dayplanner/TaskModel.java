package com.example.dayplanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TaskModel extends Pane {
    private String time;
    public TaskModel(String time,String name,boolean done,boolean isActive) throws IOException {
        if(Integer.parseInt(time.split(":")[1])<10){
            this.time= time.split(":")[0]+":0"+time.split(":")[1];
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskModel.fxml"));
        TaskController taskController = new TaskController(this.time,name,done,isActive);
        fxmlLoader.setController(taskController);
        Node view = (Node) fxmlLoader.load();
        getChildren().add(view);
    }

    public int getTime(){
        return Integer.parseInt(time.split(":")[0]);
    }
}
