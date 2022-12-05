package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button logOutBtn;

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
                String monthName="";
                String currentName;

                monthName = thatDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
                if(j==1){
                    month.setText(monthName);
                }
                try {
                    currentName = getCurrentName(j,monthName.toLowerCase());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                thatDay = new GregorianCalendar(rightNow.get(Calendar.YEAR), i, j );
                Button days = new Button(String.valueOf(j)+"\n"+currentName);
                Calendar finalThatDay = (Calendar) thatDay.clone();
                days.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-view.fxml"));
                        DayController controller = new DayController(finalThatDay);
                        fxmlLoader.setController(controller);
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage= (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                days.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
                //days.setStyle("-fx-background-color: white;");
                days.setPrefWidth(80);
                days.setPrefHeight(80);

                days.setStyle( "-fx-border-style: solid; -fx-border-width: 6px; ");


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
        System.out.println((scrollPane.getHmax()/12.0)*(rightNow.get(Calendar.MONTH)+1));

        scrollPane.setVvalue((scrollPane.getHmax()/12.0)*(rightNow.get(Calendar.MONTH)+1));
    }
    @FXML
    protected void onLogOutButtonClick(){
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader);
            Stage stage= (Stage) scrollPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getCurrentName(int j, String monthName) throws IOException {
        File file = new File("namedays/"+monthName+".csv");
        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
        List<String[]> allData = csvReader.readAll();


        ListIterator<String[]>listIterator = allData.listIterator();
        String name = "";
        String[] tmp;

        while(listIterator.hasNext()){
            tmp = listIterator.next();
            //System.out.println(tmp[1]);
            if(tmp[0].equals(String.valueOf(j))){
                name = tmp[1];

            }
        }
        //System.out.println(name);
        return name;
    }


}
