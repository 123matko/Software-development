package com.example.dayplanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*For listing tasks we created new model for task instance.*/
public class TaskModel extends Pane {
    private String time;
    private Boolean done;
    private String name;
    private Boolean isActive;
    public TaskModel(String time,String name,boolean done,boolean isActive) throws IOException {
        this.time=time;
        this.done=done;
        this.name=name;
        this.isActive=isActive;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskModel.fxml"));
        TaskController taskController = new TaskController(this,this.time,name,this.done,isActive);
        fxmlLoader.setController(taskController);
        Node view = (Node) fxmlLoader.load();
        getChildren().add(view);
    }

    //Will set status of task as done and call updateTasks method
    public void setDone() throws IOException {
        done=true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskModel.fxml"));
        TaskController taskController = new TaskController(this,this.time,name,this.done,isActive);
        fxmlLoader.setController(taskController);
        Node view = (Node) fxmlLoader.load();
        getChildren().add(view);
        updateTasks();
    }

    public int getTime(){
        return Integer.parseInt(time.split(":")[0]);
    }

    //Read all task find id in list of task that need be updated remove it and add new with updated status then all tasks write back to csv file
    public void updateTasks(){
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
            if(tmp[0].equals(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))&&tmp[1].equals(time)){
                taskToUpdate = i;
            }
            i++;
        }
        allData.remove(taskToUpdate);
        allData.add(new String[]{String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), time, name, "true"});
        FileWriter outputStream;
        CSVWriter writer = null;
        try {
            outputStream = new FileWriter(file);
            writer = new CSVWriter(outputStream);

            writer.writeAll(allData);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
