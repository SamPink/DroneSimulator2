package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class SimulationApp {
    private BorderPane bp;
    private Pane center, bottom, right;
    private Canvas canvas;
    private GraphicsContext gc;
    private ToolBar toolBar;
    private AnimationTimer timer;
    private DroneArena arena;
    private BackgroundImage backgroundImage;

    private static void handle(MouseEvent mouseEvent) {
        System.out.println("x = " + mouseEvent.getX()
                + " y = " + mouseEvent.getY());
    }

    public Scene createScene() {
        bp = new BorderPane();

        bp.setCenter(createCenter());
        bp.setTop(createBottom());
        //bp.setRight(createRight());

        return new Scene(bp, SETTINGS.SceneWidth, SETTINGS.SceneHeight);
    }

    private void loadImages() {
        Image image = new Image(SimulationApp.class.getResourceAsStream("images/background.jpg"));

        backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    private Node createCenter() {
        center = new Pane();

        center.setPadding(new Insets(20, 20, 20, 20));
        canvas = new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
        center.getChildren().addAll(canvas);
        gc = canvas.getGraphicsContext2D();

        loadImages();

        center.setBackground(new Background(backgroundImage));

        //center.setOnMousePressed(SimulationApp::handle);

        return center;
    }

    private Node createRight() {
        right = new Pane();

        right.getChildren().add(new Button("test"));

        return right;
    }

    private Node createBottom() {
        bottom = new Pane();
        toolBar = new ToolBar();

        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button spawn_1 = new Button("Spawn drone 1");

        start.setOnAction((ActionEvent) -> init());
        pause.setOnAction((ActionEvent) -> pause());
        spawn_1.setOnAction((ActionEvent) -> spawn());

        toolBar.getItems().addAll(start, pause, spawn_1);

        bottom.getChildren().add(toolBar);

        return toolBar;
    }

    private void spawn() {
        arena.getObjectManager().addMovingObject(new MovingObject1(), SETTINGS.CanvasWidth / 2, SETTINGS.CanvasHeight / 2);
    }

    private void pause() {
        timer.stop();
    }

    private void init() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                arena.updateGame(gc);
            }
        };

        arena = new DroneArena(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);

        arena.getObjectManager().moveRandom();


        timer.start();
    }
}
