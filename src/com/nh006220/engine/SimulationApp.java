package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.Objects.MovingObject1;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class SimulationApp {
    private BorderPane bp;
    private Pane center, bottom, right;
    private Canvas canvas;
    private GraphicsContext gc;
    private ToolBar toolBar;
    private AnimationTimer timer;
    private DroneArena arena;

    public Scene createScene() {
        bp = new BorderPane();

        center = new Pane();
        center.setPadding(new Insets(20, 20, 20, 20));
        canvas = new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
        center.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        right = new Pane();
        right.setStyle("-fx-background-color: grey;");

        bp.setCenter(center);
        bp.setBottom(createBottom());
        bp.setRight(right);

        return new Scene(bp, SETTINGS.SceneWidth, SETTINGS.SceneHeight);
    }

    private Node createBottom() {
        bottom = new Pane();
        toolBar = new ToolBar();

        Button start = new Button("Start");

        start.setOnAction((ActionEvent) -> init());

        toolBar.getItems().add(start);

        bottom.getChildren().add(toolBar);

        return bottom;
    }

    private void init() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        arena = new DroneArena(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);

        arena.getObjectManager().addMovingObject(new MovingObject1(), 300, 300);
        arena.getObjectManager().addMovingObject(new MovingObject1(), 100, 300);
        arena.getObjectManager().addMovingObject(new MovingObject1(), 200, 300);

        arena.getObjectManager().moveRandom();

        timer.start();
    }

    private void update() {
        arena.updateGame(gc);
    }
}
