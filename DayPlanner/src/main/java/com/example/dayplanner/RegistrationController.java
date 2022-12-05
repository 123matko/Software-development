package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegistrationController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordconf;

    /*New user can register himself even when file users.csv exist that is why we test if first.
    * If this file exist we load data to get number of id for next user, then we store all data back to csv file.*/
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

        if(passwordtext.equals(passwordconftext)){                      //New user can be registered only if password and confirm password match
            idOfLast++;

            String[] data1 = { String.valueOf(idOfLast), usernametext, passwordtext };
            allData.add(data1);
            writer.writeAll(allData);
            writer.close();
            System.out.println("Registered!");
            System.out.println("Username: "+ usernametext+", Password: "+passwordtext);
            try {                                                       //After successful registration view is changed to main view.
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
