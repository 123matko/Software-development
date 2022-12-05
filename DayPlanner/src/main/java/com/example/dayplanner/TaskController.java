package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    private String time;
    private String name;
    private boolean done;
    private boolean isActual;

    @FXML
    private Label timeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label doneLabel;

    private TaskModel taskModel;

    public TaskController(TaskModel taskModel,String time,String name,boolean done,boolean isActual){
        super();
        this.time = time;
        this.name = name;
        this.done = done;
        this.isActual = isActual;
        this.taskModel = taskModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeLabel.setText(time);
        nameLabel.setText(name);
        doneLabel.setText(done?"DONE":"NOT YET");
        doneLabel.setOnMouseClicked(mouseEvent -> {
            try {
                taskModel.setDone();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(isActual){
            Background background = new Background(new BackgroundFill(Color.rgb(255,127,65),null,null));
            timeLabel.setBackground(background);
            doneLabel.setBackground(background);

        }
        if(done){
            Background background = new Background(new BackgroundFill(Color.rgb(51,196,179),null,null));
            timeLabel.setBackground(background);
            doneLabel.setBackground(background);
        }
    }


}
