package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddTaskController implements Initializable {
    private Calendar calendar;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label dayLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField taskName;
    @FXML
    private Spinner<Integer> hour;
    @FXML
    private Spinner<Integer> minute;
    private boolean isValid=true;


    public AddTaskController(Calendar calendar){
        super();
        this.calendar= calendar;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dayLabel.setText(calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        cancelButton.setOnMouseClicked(mouseEvent -> {
            backToDayView();
        });

        saveButton.setOnMouseClicked(mouseEvent -> {
            isValid=isValid();
            String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            File file = new File("tasks/"+monthName+".csv");
            FileReader fileReader;
            FileWriter outputStream;
            CSVWriter writer = null;
            List<String[]> allData= new ArrayList<>();
            if(!file.exists()){
                try {
                    file.createNewFile();
                    outputStream = new FileWriter(file);
                    writer = new CSVWriter(outputStream);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                try {
                    fileReader = new FileReader(file);
                    CSVReader csvReader = new CSVReaderBuilder(fileReader)
                            .withSkipLines(0)
                            .build();
                    allData = csvReader.readAll();
                    fileReader.close();
                    outputStream = new FileWriter(file);
                    writer = new CSVWriter(outputStream);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            String taskNameText = taskName.getText();
            int hourValue = hour.getValue();
            int minuteValue = minute.getValue();
            String time = hourValue+":"+(minuteValue<10?"0"+minuteValue:minuteValue);

            System.out.println(isValid);
            if(isValid) {
                String[] data1 = {String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), time, taskNameText,"false"};
                allData.add(data1);
                assert writer != null;
                writer.writeAll(allData);
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                backToDayView();
            }else {
                errorLabel.setText("In this time you already have one task!");
                assert writer != null;
                writer.writeAll(allData);
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public boolean isValid(){
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        File file = new File("tasks/"+monthName+".csv");

        FileReader fileReader;

        List<String[]> allData= new ArrayList<>();
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                fileReader = new FileReader(file);
                CSVReader csvReader = new CSVReaderBuilder(fileReader)
                        .withSkipLines(0)
                        .build();
                allData = csvReader.readAll();
                fileReader.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String taskNameText = taskName.getText();
        int hourValue = hour.getValue();
        int minuteValue = minute.getValue();
        ListIterator<String[]>listIterator = allData.listIterator();
        String time = hourValue+":"+minuteValue;
        String[] tmp;
        while(listIterator.hasNext()){
            tmp = listIterator.next();
            System.out.println(tmp[1]);
            if(tmp[0].equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))&&tmp[1].equals(time)){
                return false;

            }
        }
        return true;
    }

    //Return
    public void backToDayView(){
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
    }

}
