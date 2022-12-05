package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      username.setFocusTraversable(false);
      password.setFocusTraversable(false);
    }

    /*When user tries to log in we read all data from users.csv file.
    If given username exist and match password then we redirect user to main view. */
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

    //When user click on registration button we change view to registration-view
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
