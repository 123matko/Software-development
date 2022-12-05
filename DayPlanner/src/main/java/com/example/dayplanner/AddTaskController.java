package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AddTaskController implements Initializable {
    private Calendar calendar;
    @FXML
    private Button cancelButton;

    @FXML
    private Label dayLabel;

    public AddTaskController(Calendar calendar){
        super();
        this.calendar= calendar;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dayLabel.setText(calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        cancelButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-view.fxml"));
                DayController controller = new DayController(calendar);
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage= (Stage) cancelButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

}
