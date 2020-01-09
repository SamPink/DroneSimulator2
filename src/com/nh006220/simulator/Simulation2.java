package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.GameWorld;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.simulator.Objects.MovingObject1;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Simulation2 extends GameWorld {

    public Simulation2(String title, int fps) {
        super(title, fps);
    }

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
        newGame.setOnAction(actionEvent -> updateScene(arenaBuilder()));
        Button openGame = new Button("Open Game");
        openGame.setOnAction(actionEvent -> updateScene(createGame()));
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

    private Pane createGame(DroneArena arena) {
        BorderPane bp = new BorderPane();

        setArena(arena);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                onFrame();
            }
        };

        setGameLoop(timer);

        center.setPadding(new Insets(20, 20, 20, 20));
        canvas = new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
        center.getChildren().addAll(canvas);
        gc = canvas.getGraphicsContext2D();

        loadImages(); //TODO if don't have image load

        center.setBackground(new Background(backgroundImage));

        bp.setCenter(center);
        bp.setTop(getToolBar());

        return bp;
    }

    @Override
    public Pane createGame() {
        return createGame(new DroneArena());
    }

    private Pane arenaBuilder() {
        BorderPane builder = new BorderPane();
        DroneArena arena = new DroneArena();

        Text title = new Text("Arena Creator");
        title.setFont(new Font("Juice ITC", 40));

        Button drone1 = new Button("Drone 1");
        drone1.setOnAction(actionEvent -> {
            arena.getObjectManager().addMovingObject(new MovingObject1());
            builder.setCenter(listView(arena));
        });
        Button start = new Button("Start current");
        start.setOnAction(actionEvent -> updateScene(createGame(arena)));
        VBox addObjects = new VBox(drone1, start);



        builder.setTop(title);
        builder.setCenter(listView(arena));
        builder.setRight(addObjects);

        return builder;
    }

    private Node listView(DroneArena arena) {
        ListView arenaContent = new ListView();

        for (Object obj : arena.getObjectManager().getAllObjects()) {
            arenaContent.getItems().add(obj.toString());
        }

        return arenaContent;
    }

    @Override
    protected void onFrame() {
        getArena().updateGame(gc);
    }

    private void loadImages() {
        Image image = new Image(Simulation2.class.getResourceAsStream("images/background.jpg"));

        backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }
}
