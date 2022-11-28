package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.opencsv.*;
import javafx.stage.Stage;


public class RegistrationController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordconf;


    @FXML
    protected void onRegisterButtonClick() throws IOException {
        File users = new File("users.csv");
        FileWriter outputStream ;
        FileReader fileReader;
        CSVWriter writer = null;
        List<String[]> allData= new ArrayList<>();
        int idOfLast = -1;
        if(!users.exists()){
            try {
                users.createNewFile();
                outputStream = new FileWriter(users);
                writer = new CSVWriter(outputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                fileReader = new FileReader(users);


                CSVReader csvReader = new CSVReaderBuilder(fileReader)
                        .withSkipLines(0)
                        .build();
                allData = csvReader.readAll();
                fileReader.close();
                idOfLast = allData.size()-1;
                outputStream = new FileWriter(users);
                writer = new CSVWriter(outputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String usernametext= username.getText();
        String passwordtext = password.getText();
        String passwordconftext = passwordconf.getText();
        if(passwordtext.equals(passwordconftext)){
            idOfLast++;

            String[] data1 = { String.valueOf(idOfLast), usernametext, passwordtext };
            allData.add(data1);
            writer.writeAll(allData);
            writer.close();
            System.out.println("Registered!");
            System.out.println("Username: "+ usernametext+", Password: "+passwordtext);
            try {
                Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader);
                Stage stage= (Stage) username.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            writer.close();
        }
    }
}
