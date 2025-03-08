package org.cs4076_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class App extends Application {
    private Stage stage;
    final int minWidth = 800;
    final int minHeight = 600;
    public String moduleString;
    public String dateString;

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

        HBox homeLayout = new HBox(new VBox(header, new Label(""), subtitle, classDir, scheduleView, exitButton));
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setSpacing(10);
        homeLayout.setStyle("-fx-background-color: #dceae2");
        homeLayout.setMinSize(500,500);

        //return changes
        return new Scene(homeLayout, minHeight, minWidth);
    }

    private Scene manageClasses(){

// ----------------------- POPUP -----------------------

        Popup popup = new Popup();

        Label popText = new Label("");
        popText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox popBox = new VBox(
                popText
        );
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
            String m = tcp.run();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                popText.setText(m);
                popup.show(stage);
            } else {
                popText.setText(tcp.send("ADD_" + input));
                popup.show(stage);
            }
        });

        Button remClass = new Button("Remove Class");
        remClass.setMinWidth(100);
        remClass.setOnAction(e -> {
            TCPClient_23355433 tcp = new TCPClient_23355433();
            String m = tcp.run();
            String input = module.getText() + "_" + room.getText() + "_" + date.getValue().toString() + "_" + time.getValue();
            if (!Objects.equals(m, "OK")) {
                popText.setText(m);
                popup.show(stage);
            } else {
                popText.setText(tcp.send("REMOVE_" + input));
                popup.show(stage);
            }
        });

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));

        VBox col1 = new VBox(
                new Label("Module Code:"),
                new Label("Room:"),
                new Label("Date:"),
                new Label("Time:"),
                new Label("")
        );
        col1.setSpacing(10);

        VBox col2 = new VBox(
                module,
                room,
                date,
                time
        );
        col2.setSpacing(2);

        HBox columns = new HBox(
                col1,
                col2
        );
        columns.setSpacing(5);

        HBox buttons = new HBox(
                addClass,
                remClass
        );
        buttons.setSpacing(10);

        VBox layout = new VBox(
                header,
                new Label(""),
                columns,
                buttons,
                new Label(""),
                homeBtn
        );
        layout.setAlignment(Pos.CENTER_LEFT);

        layout.setAlignment(Pos.CENTER_LEFT);

        layout.setStyle("-fx-background-color: #dceae2");
        layout.setMinSize(500,500);
        return new Scene(layout, minWidth, minHeight);
    }

    private Scene viewSchedule() {

        Label header = new Label("View Schedule");
        header.setFont(Font.font("comic sans ms", FontWeight.EXTRA_BOLD, 20));

        DatePicker date = new DatePicker();
        TextField module = new TextField();

        module.setMinWidth(200);
        module.setPromptText("e.g., CSXXXX");
        date.setMinWidth(200);
        date.setPromptText("Select Date");

        TextArea scheduleField = new TextArea("");
        scheduleField.setEditable(false);
        scheduleField.setMaxWidth(400);
        scheduleField.setMinWidth(400);
        scheduleField.setMaxHeight(400);
        scheduleField.setMinHeight(400);

        Label result = new Label("");

        Button homeBtn = new Button("Home");
        homeBtn.setOnAction(e -> stage.setScene(homeMenu()));

// ----------------------- LAYOUT -----------------------

        VBox col1 = new VBox(
                new Label("Module Code:"),
                new Label("Date:"),
                new Label("")
        );
        col1.setSpacing(10);

        VBox col2 = new VBox(
                module,
                date
        );
        col2.setSpacing(2);

        HBox columns = new HBox(
                col1,
                col2
        );
        columns.setSpacing(5);

        VBox schedDisplay = new VBox(
                scheduleField
        );
        schedDisplay.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(
                header,
                new Label(""),
                columns,
                result,
                new Label(""),
                homeBtn
        );
        layout.setAlignment(Pos.CENTER_LEFT);

        HBox sides = new HBox(
                layout,
                schedDisplay
        );
        sides.setSpacing(10);
        layout.setStyle("-fx-background-color: #dceae2");
        return new Scene(sides, minWidth, minHeight);
    }

    //launcher
    public static void main(String[] args) {
        launch();
    }
}
