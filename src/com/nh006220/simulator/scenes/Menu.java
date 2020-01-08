package com.nh006220.simulator.scenes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu extends Pane {
    private BorderPane bp;

    public Menu() {
        bp = new BorderPane();
        bp.setTop(setTop());
        bp.setCenter(setCenter());
        getChildren().add(bp);
    }

    private Node setCenter() {
        Text text = new Text("Drone Simulator");

        text.setFont(new Font("Juice ITC", 93));

        Button newGame = new Button("New Game");
        newGame.setOnAction(actionEvent -> {

        });
        Button openGame = new Button("Open Game");
        Button settings = new Button("Settings");

        VBox vBox = new VBox(newGame, openGame, settings);

        Pane pane = new Pane(text, vBox);
        pane.setTranslateX(500);
        pane.setTranslateY(500);
        pane.setPadding(new Insets(300));

        return pane;
    }

    private Node setTop() {
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button addDrone = new Button("add Drone");

        ToolBar toolBar = new ToolBar(start, stop, addDrone);

        return new HBox(toolBar);
    }


}
