package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Calendar rightNow = Calendar.getInstance();
        VBox root = new VBox();
        Calendar thatDay ;
        for (int i=0;i<12;i++) {
            GridPane calendarPane = new GridPane();
            Label month = new Label();
            thatDay = new GregorianCalendar(rightNow.get(Calendar.YEAR), i, 1);
            for (int j = 1; j < thatDay.getActualMaximum(Calendar.DAY_OF_MONTH) + 1; j++) {
                Button days = new Button(String.valueOf(j));
                days.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
                //days.setStyle("-fx-background-color: white;");
                days.setPrefWidth(80);
                days.setPrefHeight(80);

                days.setStyle( "-fx-border-style: solid; -fx-border-width: 6px; ");
                thatDay = new GregorianCalendar(rightNow.get(Calendar.YEAR), i, j );

                if(j==1){
                    String monthName = thatDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

                    month.setText(monthName);

                }
                int day = thatDay.get(Calendar.DAY_OF_WEEK);
                int weak = thatDay.get(Calendar.WEEK_OF_MONTH);
                //System.out.println(j + "," + day + "," + weak);

                if (day != 1) {
                    days.setStyle("-fx-border-color: #15BDD8;");
                    calendarPane.add(days, day - 2, weak);
                } else {
                    days.setStyle(" -fx-border-color: #FF3E3E; ");

                    calendarPane.add(days, 6, weak);
                }
                if(rightNow.get(Calendar.DAY_OF_MONTH)==j&& rightNow.get(Calendar.MONTH)==i){
                    days.setStyle("-fx-border-color: #FF7F41;");
                }
            }
            root.getChildren().add(month);
            root.getChildren().add(calendarPane);
        }
        scrollPane.setContent(root);
        scrollPane.setPannable(true);
    }


}
