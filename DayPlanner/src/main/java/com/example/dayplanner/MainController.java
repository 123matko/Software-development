package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private GridPane calendarPane;
    private Button[] days ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calendar rightNow = Calendar.getInstance();
        for(int i=0;i<rightNow.getMaximum(Calendar.DAY_OF_MONTH)-1;i++){

        }
        rightNow.setFirstDayOfWeek(Calendar.MONDAY);
        Button sat = new Button(String.valueOf(rightNow.get(Calendar.DAY_OF_MONTH)));
        int day = rightNow.get(Calendar.DAY_OF_WEEK);
        int weak = rightNow.get(Calendar.WEEK_OF_MONTH);
        if(day!=0) {
            calendarPane.add(sat, day-1, weak);
        }



    }
}
