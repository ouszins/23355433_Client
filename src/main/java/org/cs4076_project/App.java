package org.cs4076_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;

import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class App extends Application {
    final int minWidth = 800;
    final int minHeight = 600;
    public String moduleString;
    public String dateString;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("UL LectureScheduler");

        primaryStage.setTitle("Lecture Scheduler");

        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);
        primaryStage.setResizable(false); //size of stage cannot be adjusted
        primaryStage.setScene(homeMenu());
        primaryStage.show();
    }

    //home menu
    private Scene homeMenu() {
        Label header = new Label("Home Screen");
        Label subtitle = new Label("What would you like to adjust or view?");
        Button classDir = new Button("Manage your Classes");
        Button scheduleView = new Button("View Schedule");
        Button exitButton = new Button("Exit");

        header.setFont(Font.font("Comic Sans", FontWeight.EXTRA_BOLD, 30));
        subtitle.setFont(Font.font("Comic Sans", FontWeight.BOLD, 10));
        subtitle.setTextFill(Color.web("#92a69b"));

        classDir.setMinWidth(200);
        scheduleView.setMinWidth(200);
        exitButton.setMinWidth(200);

        exitButton.setOnAction(e -> System.exit(0));

        HBox homeLayout = new HBox(new VBox(header, new Label(""), subtitle, classDir, scheduleView, exitButton));
        homeLayout.setAlignment(Pos.CENTER_LEFT);
        homeLayout.setSpacing(10);
        homeLayout.setStyle("-fx-background-color: #dceae2");
        homeLayout.setMinSize(500,500);

        //return changes
        return new Scene(homeLayout, minHeight, minWidth);
    }

    //launcher
    public static void main(String[] args) {
        launch();
    }
}
