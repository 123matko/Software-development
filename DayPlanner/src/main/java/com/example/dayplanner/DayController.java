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
import java.util.GregorianCalendar;
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
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button newTaskButton;
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
        nextButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-view.fxml"));
                DayController controller = new DayController(new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+1));
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage= (Stage) nextButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        previousButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-view.fxml"));
                DayController controller = new DayController(new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1));
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage= (Stage) nextButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        newTaskButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addTask-view.fxml"));
                AddTaskController controller = new AddTaskController(calendar);
                fxmlLoader.setController(controller);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage= (Stage) nextButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }



}
