package com.example.dayplanner;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddTaskController implements Initializable {
    private Calendar calendar;

    public AddTaskController(Calendar calendar){
        super();
        this.calendar= calendar;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
