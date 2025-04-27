package org.cs4076_project;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;


public class clientApp extends Application {
    private Stage stage;
    private GridPane scheduleGrid;
    private String[] weekdays = {" Monday", " Tuesday", "Wednesday", " Thursday", "  Friday"};
    private String[] moduleTime = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};

    final int minWidth = 800;
    final int minHeight = 1050;
    public String moduleString;
    public String dateString;
    Region spacer = new Region();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("UL LectureScheduler");

        stage.setTitle("Lecture Scheduler");

        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);
        stage.setResizable(false); //size of stage cannot be adjusted
        stage.setScene(homeMenu());
        stage.show();
    }

    //home menu
    private Scene homeMenu() {
        Label header = new Label("Home Screen");
        Label subtitle = new Label("What would you like to adjust or view?");
        Button classDir = new Button("Manage your Classes");
        Button scheduleView = new Button("View Schedule");
        Button exitButton = new Button("Exit");

        header.setFont(Font.font("comic sans ms", FontWeight.EXTRA_BOLD, 30));
        subtitle.setFont(Font.font("comic sans ms", FontWeight.BOLD, 10));
        header.setTextFill(Color.web("#0B3E2CFF"));
        subtitle.setTextFill(Color.web("#92a69b"));

        classDir.setMinWidth(200);
        scheduleView.setMinWidth(200);
        exitButton.setMinWidth(200);

        classDir.setOnAction(e -> {stage.setScene(manageClasses());});
        scheduleView.setOnAction(e -> {stage.setScene(viewSchedule());});
        exitButton.setOnAction(e -> Platform.exit());

        spacer.setPrefHeight(150);
        HBox homeLayout = new HBox(new VBox(spacer, header, new Label(""), subtitle, new Label(""), classDir, scheduleView, exitButton));
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setSpacing(10);
        homeLayout.setStyle("-fx-background-color: #dceae2");
        homeLayout.setMinSize(500,500);
        homeLayout.setMaxHeight(300);
        homeLayout.setMaxWidth(800);

        //return changes
        return new Scene(homeLayout, 800, 300);
    }

    private Scene manageClasses(){

// ----------------------- POPUP -----------------------

        Popup popup = new Popup();

        Label popText = new Label("");
        popText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox popBox = new VBox(popText);
        popBox.setStyle("-fx-background-color: #ceded4; -fx-padding: 10px; -fx-border-color: #637163; -fx-border-width: 2px;");

        popup.getContent().add(popBox);

        // when user clicks outside popup, popup hides
        popup.setAutoHide(true);


// ----------------------- SCENE -----------------------

        Label header = new Label("Manage Classes");
        header.setFont(Font.font("comic sans ms", FontWeight.EXTRA_BOLD, 20));

        TextField module = new TextField();
        TextField room = new TextField();
        DatePicker date = new DatePicker();
        ComboBox time = new ComboBox();

        module.setPromptText("e.g., CSXXXX");
        room.setPromptText("e.g., CSG001");
        date.setPromptText("Select Date");
        time.setPromptText("Select time");

        date.setMinWidth(200);

        ArrayList<String> list = new ArrayList<>(Arrays.asList("9", "10", "11", "12", "13", "14", "15", "16", "17"));
        ObservableList<String> times = FXCollections.observableList(list);
        time.setItems(times);

        Button addClass = new Button("Add Class");
        addClass.setMinWidth(100);
        addClass.setOnAction(e -> {
            TCPClient_23355433 tcp = new TCPClient_23355433();
            tcp.run();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();

                popText.setText(tcp.send("ADD_" + input));
                tcp.close();
                popup.show(stage);
                
            
        });

        Button removeClass = new Button("Remove Class");
        removeClass.setMinWidth(100);
        removeClass.setOnAction(e -> {
            TCPClient_23355433 tcp = new TCPClient_23355433();
            tcp.run();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            
                popText.setText(tcp.send("REMOVE_" + input));
                tcp.close();
                popup.show(stage);
        });

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));

        VBox col1 = new VBox(new Label("Module Code:"), new Label("Room:"), new Label("Date:"), new Label("Time:"), new Label(""));
        col1.setSpacing(10);

        VBox col2 = new VBox(module, room, date, time);
        col2.setSpacing(2);

        HBox columns = new HBox(col1, col2);
        columns.setSpacing(5);
        columns.setAlignment(Pos.CENTER);

        HBox buttons = new HBox( addClass, removeClass);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        spacer.setPrefWidth(150);
        VBox layout = new VBox(header, new Label(""), columns, buttons, new Label(""), homeBtn);
        layout.setAlignment(Pos.CENTER);

        layout.setAlignment(Pos.CENTER);

        layout.setStyle("-fx-background-color: #dceae2");
        layout.setMinSize(500,500);
        return new Scene(layout, minWidth, minHeight);
    }

    private Scene viewSchedule() {

        Label header = new Label("View Schedule");
        header.setFont(Font.font("comic sans ms", FontWeight.EXTRA_BOLD, 20));

        Button earlyLectures = new Button("Push Lectures Early");

        earlyLectures.setOnAction(e -> {
            TCPClient_23355433 tcp = new TCPClient_23355433();
            tcp.run();
            String response = tcp.send("EARLY_" + earlyLectures.getText()); // Get schedule data
            tcp.close();
        });

        earlyLectures.setMinWidth(200);

        //creating schedule fields for the 5 working days, Mon-Fri

        // ---------------------------------- WEEKDAYS ----------------------------------------
        final GridPane scheduleGrid = new GridPane();
        scheduleGrid.getChildren().clear();

        scheduleGrid.setHgap(15);
        scheduleGrid.setVgap(5);

        //loop for inserting the weekdays
        for (int col = 0; col < weekdays.length; col++) {
            Label weekday = new Label(weekdays[col]);
            weekday.setFont(Font.font("comic sans ms", FontWeight.BOLD, 17));
            weekday.setPadding(new Insets(0,0,10,5));
            weekday.setMaxWidth(200);
            scheduleGrid.add(weekday, col + 1, 0);
        }

        TextArea modules = null;
        for (int row = 0; row < moduleTime.length; row++) {
            Label times = new Label(moduleTime[row]);
            times.setStyle("-fx-font-size: 15");
            times.setRotate(90);
            scheduleGrid.add(times, 0, row + 1);

            //loop for inserting the actual module boxes
            for (int col = 0; col < weekdays.length; col++) {
                modules = new TextArea();
                modules.setEditable(false);
                modules.setMinSize(100, 100);
                modules.setMaxSize(100, 100);
                scheduleGrid.add(modules, col + 1, row + 1);
            }
        }

        Label result = new Label("");

        //Buttons
        Button viewBtn = new Button("View Schedule");
        viewBtn.setMinWidth(200);
        Button homeBtn = new Button("Home");

        //Use in buttons
        TextArea finalModules = modules;
        viewBtn.setOnAction(e ->{
            TCPClient_23355433 tcp = new TCPClient_23355433();
            tcp.run();
            String response = tcp.send("DISPLAY_" + earlyLectures.getText()); // Get schedule data
            tcp.close();

            Alert updateAl = new Alert(Alert.AlertType.NONE, "Lectures have been updated", ButtonType.OK);
            updateAl.showAndWait();

            if (response == null || response.isEmpty()) {
                Alert notHere = new Alert(Alert.AlertType.ERROR, "No Lectures here !", ButtonType.OK);
                notHere.showAndWait();
                return;
            }

            // Parse response from server
            String[] lectures = response.split("/");

            for (String lecture : lectures) {
                String[] details = lecture.split("_");


                String moduleCode = details[0];
                String room = details[1];
                String fdate = details[2];
                String time = details[3]+":00";
                String[] dateParts = fdate.split("-");
                int day= Integer.valueOf(dateParts[2]);

                if(day>28){
                    day=day-28;
                } else if (day>21){
                    day=day-21;
                }else if (day>14){
                    day=day-14;
                }else if (day>7){
                    day=day-7;
                }

                int colIndex = day ;

                int rowIndex = -1;
                for (int i = 0; i < moduleTime.length; i++) {
                    if (moduleTime[i].equals(time)) {
                        rowIndex = i + 1;
                        break;
                    }
                }


                if (colIndex != -1 && rowIndex != -1) {
                    TextArea cell = new TextArea(moduleCode + "\n" + room);
                    cell.setEditable(false);
                    cell.setMinSize(100, 100);
                    cell.setMaxSize(100, 100);
                    scheduleGrid.add(cell, colIndex, rowIndex);
                }
            }
        });

        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));
// ----------------------- LAYOUT -----------------------

        VBox col1 = new VBox();
        col1.setSpacing(0);

        VBox col2 = new VBox(earlyLectures);
        col2.setSpacing(0);

        HBox columns = new HBox(col1, col2);
        columns.setSpacing(0);
        columns.setPadding(new Insets(0, 10, 0, 0));

        spacer.prefHeight(5);
        scheduleGrid.setStyle("-fx-background-color: #658e65");
        HBox schedGrid = new HBox(scheduleGrid);
        VBox schedDisplay = new VBox(schedGrid);
        schedDisplay.setAlignment(Pos.TOP_RIGHT);
        schedDisplay.setStyle("-fx-background-color: #ccdfd7");

        VBox layout = new VBox(header, new Label(""), columns, result, viewBtn, new Label(""), homeBtn);
        layout.setAlignment(Pos.CENTER_LEFT);

        //padding so that the layout of these aren't off
        scheduleGrid.setPadding(new Insets(5, 10, 50, 35));
        layout.setPadding(new Insets(5, 0, 5, 10));

        HBox sides = new HBox(layout, schedDisplay);
        sides.setStyle("-fx-background-color: #658e65;");
        layout.setStyle("-fx-background-color: #cbded6");

        return new Scene(sides, 950, 600);
    }

    //launcher
    public static void main(String[] args) {
        launch();
    }
}
