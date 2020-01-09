package com.nh006220.simulator.scenes;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.GameWorld;
import com.nh006220.simulator.MovingObject1;
import com.nh006220.simulator.SETTINGS;
import com.nh006220.simulator.SimulationApp;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Simulation2 extends GameWorld {
    Stage stage;
    Pane center = new Pane();
    Canvas canvas;
    GraphicsContext gc;
    BackgroundImage backgroundImage = null;

    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     *
     * @param title - Title of the application window.
     */
    public Simulation2(String title, int fps) {
        super(title, fps);
    }

    /**
     * Initialize the game world by update the JavaFX Stage.
     *
     * @param primaryStage The main window containing the JavaFX Scene.
     */
    @Override
    public void initialize(Stage primaryStage) {
        stage = primaryStage;

        primaryStage.setTitle(getWindowTitle());

        setSceneNodes(createMenu());
        setGameSurface(new Scene(getScene(), SETTINGS.SceneWidth, SETTINGS.SceneHeight));
        primaryStage.setScene(getGameSurface());
    }

    @Override
    public Pane createMenu() {
        BorderPane bp = new BorderPane();
        Text text = new Text("Drone Simulator");

        text.setFont(new Font("Juice ITC", 93));

        Button newGame = new Button("New Game");
        newGame.setOnAction(actionEvent -> {
            updateScene(createGame());
        });
        Button openGame = new Button("Open Game");
        Button settings = new Button("Settings");

        VBox vBox = new VBox(newGame, openGame, settings);

        Pane pane = new Pane(text, vBox);
        pane.setTranslateX(500);
        pane.setTranslateY(500);
        pane.setPadding(new Insets(300));

        bp.setTop(getToolBar());
        bp.setCenter(pane);

        return bp;
    }

    private ToolBar getToolBar() {
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button addDrone = new Button("add Drone");

        addDrone.setOnAction(actionEvent -> spawn());

        return new ToolBar(start, stop, addDrone);
    }

    private void updateScene(Pane scene) {
        setSceneNodes(scene);
        setGameSurface(new Scene(getScene(), SETTINGS.SceneWidth, SETTINGS.SceneHeight));
        stage.setScene(getGameSurface());
    }

    @Override
    public Pane createGame() {
        BorderPane bp = new BorderPane();
        setArena(new DroneArena(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight));
        AnimationTimer timer = getTimer();

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                onFrame();
            }
        };

        setGameLoop(timer);

        getTimer().start();

        center.setPadding(new Insets(20, 20, 20, 20));
        canvas = new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
        center.getChildren().addAll(canvas);
        gc = canvas.getGraphicsContext2D();

        loadImages();

        center.setBackground(new Background(backgroundImage));

        bp.setCenter(center);
        bp.setTop(getToolBar());


        return bp;

    }

    @Override
    protected void onFrame() {
        getArena().updateGame(gc);
    }

    private void loadImages() {
        Image image = new Image(SimulationApp.class.getResourceAsStream("images/background.jpg"));

        backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    private void spawn() {
        getArena().getObjectManager().addMovingObject(new MovingObject1(), SETTINGS.CanvasWidth / 2, SETTINGS.CanvasHeight / 2);
    }
}
