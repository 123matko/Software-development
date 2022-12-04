package com.example.dayplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DayController implements Initializable {
    private Calendar calendar;

    public DayController (Calendar calendar){
        super();
        this.calendar= calendar;

    }
    @FXML
    private Label dateLabel;
    @FXML
    private Button backButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.YEAR));
        dateLabel.setText(calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        backButton.setOnMouseClicked(mouseEvent ->{
            try {
                Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader);
                Stage stage= (Stage) dateLabel.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }



}
