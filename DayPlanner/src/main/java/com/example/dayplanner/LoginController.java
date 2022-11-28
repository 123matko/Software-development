package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    protected void onLoginButtonClick() throws IOException{
        File users = new File("users.csv");
        FileReader fileReader = new FileReader(users);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(0).build();
        List<String[]> allData = csvReader.readAll();

        String usernametext= username.getText();
        String passwordtext = password.getText();

        ListIterator<String[]>listIterator = allData.listIterator();
        boolean isRegistered = false;
        String[] tmp;

        while(listIterator.hasNext()){
            tmp = listIterator.next();

            if(tmp[1].equals(usernametext) && tmp[2].equals(passwordtext)){
                isRegistered = true;
                break;
            }
        }


        if(isRegistered){
            try {
                Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader);
                Stage stage= (Stage) username.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) {
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("registration-view.fxml"));
            Scene scene = new Scene(fxmlLoader);
            Stage stage= (Stage) username.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
