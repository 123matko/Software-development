package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private GridPane calendarPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Calendar rightNow = Calendar.getInstance();
        rightNow.setFirstDayOfWeek(Calendar.MONDAY);
        System.out.println(rightNow.getMaximum(Calendar.DAY_OF_MONTH));

        for(int i=0;i<rightNow.getMaximum(Calendar.DAY_OF_MONTH);i++){
            Button days = new Button(String.valueOf(i));
            days.setStyle("-fx-border-color: #15BDD8");
            Calendar thatDay = new GregorianCalendar(rightNow.get(Calendar.YEAR),rightNow.get(Calendar.MONTH),i+1);
            int day = thatDay.get(Calendar.DAY_OF_WEEK);
            int weak = thatDay.get(Calendar.WEEK_OF_MONTH);
            System.out.println(i+","+day+","+weak);
            if(day!=1) {
                calendarPane.add(days, day-2, weak);
            }else {
                calendarPane.add(days, 6, weak);
            }

        }





    }
}
