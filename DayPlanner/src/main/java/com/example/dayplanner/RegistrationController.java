package com.example.dayplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.List;
import java.util.Objects;

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
                outputStream = new FileWriter(users);
                writer = new CSVWriter(outputStream);

                CSVReader csvReader = new CSVReaderBuilder(fileReader)
                        .withSkipLines(1)
                        .build();
                List<String[]> allData = csvReader.readAll();
                idOfLast = Integer.parseInt(allData.get(allData.size() - 1)[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String usernametext= username.getText();
        String passwordtext = password.getText();
        String passwordconftext = passwordconf.getText();
        System.out.println(writer.checkError());
        if(passwordtext.equals(passwordconftext)){
            idOfLast++;
            String[] data1 = { String.valueOf(idOfLast), usernametext, passwordtext };
            writer.writeNext(data1);
            writer.close();
            System.out.println(" registered!");
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
