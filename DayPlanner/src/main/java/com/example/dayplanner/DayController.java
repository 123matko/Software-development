package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DayController implements Initializable {
    private final Calendar calendar;

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
    @FXML
    private ScrollPane taskList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.YEAR));
        dateLabel.setText(calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR));
        try {
            initializeTasks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        backButton.setOnMouseClicked(mouseEvent ->{
            try {
                Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader);
                Stage stage = (Stage) dateLabel.getScene().getWindow();
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
        notificationPopUp();
    }

    public void notificationPopUp(){
        Calendar calendar= Calendar.getInstance();
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        File file = new File("tasks/"+monthName+".csv");

        FileReader fileReader;
        List<String[]> allData= new ArrayList<>();
        if(file.exists()) {
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
        String timeU="";
        ListIterator<String[]> listIterator = allData.listIterator();
        String[] tmp;
        ArrayList<TaskModel> tasks= new ArrayList<TaskModel>();
        int i=0;
        int taskToUpdate = 0;
        while(listIterator.hasNext()){
            tmp = listIterator.next();
            if(tmp[0].equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))){
                int timediff = (Integer.parseInt(tmp[1].split(":")[1]) - calendar.get(Calendar.MINUTE) );
                if( timediff <= 5 && timediff >= 0 && (Integer.parseInt(tmp[1].split(":")[0]) - calendar.get(Calendar.HOUR_OF_DAY) ) == 0) {
                    System.out.println("Inside");
                    Label secondLabel = new Label(tmp[1] + " -> " + tmp[2]);
                    StackPane secondaryLayout = new StackPane();
                    secondaryLayout.getChildren().add(secondLabel);
                    Scene secondScene = new Scene(secondaryLayout, 230, 100);
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Reminder");
                    newWindow.setScene(secondScene);
                    newWindow.show();
                    newWindow.setAlwaysOnTop(true);
                }
            }
            i++;
        }
    }

    public void initializeTasks() throws IOException {
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        File file = new File("tasks/"+monthName+".csv");

        FileReader fileReader;
        VBox root = new VBox();
        List<String[]> allData= new ArrayList<>();
        if(file.exists()) {
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
        String taskName = "";
        String time = "";
        boolean done = false;
        boolean isActual = false;

        ListIterator<String[]>listIterator = allData.listIterator();
        String[] tmp;
        ArrayList<TaskModel> tasks= new ArrayList<TaskModel>();
        while(listIterator.hasNext()){
            isActual=false;

            tmp = listIterator.next();
            System.out.println(tmp[1]);
            if(tmp[0].equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))){
                System.out.println(Arrays.toString(tmp));
                time=tmp[1];
                taskName=tmp[2];
                done= tmp[3].equals("true");
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                if(calendar.before(now)) {
                    if(Integer.parseInt(time.split(":")[0])==hour) {
                        isActual = true;
                    }
                }
                System.out.println(time.split(":")[0]+" , "+hour+"="+isActual);
                tasks.add(new TaskModel(time,taskName,done,isActual));

            }
        }
        tasks.sort(new Comparator<TaskModel>() {
            @Override
            public int compare(TaskModel o1, TaskModel o2) {
                if (o1.getTime() < o2.getTime()) {
                    return -1;
                }
                return 1;
            }
        });
        for(TaskModel task:tasks) {
            root.getChildren().add(task);
        }
       taskList.setContent(root);
    }

}
