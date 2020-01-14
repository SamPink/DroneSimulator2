package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.GameWorld;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.Objects.MovingObject2;
import com.nh006220.simulator.Objects.StaticObject1;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        Button openGame = new Button("Open Game");
        Button settings = new Button("Settings");
        Button openHelp = new Button("Open Help");

        newGame.setOnAction(actionEvent -> updateScene(arenaBuilder()));
        openGame.setOnAction(actionEvent -> load());
        openHelp.setOnAction(actionEvent -> updateScene(new Pane(new TextArea("Helpful information"))));

        VBox vBox = new VBox(newGame, openGame, settings, openHelp);

        Pane pane = new Pane(text, vBox);
        pane.setTranslateX(500);
        pane.setTranslateY(500);
        pane.setPadding(new Insets(300));

        bp.setTop(getToolBar());
        bp.setCenter(pane);

        return bp;
    }

    private <TODO> Pane createGame(DroneArena arena) {
        bpGame = new BorderPane();

        setArena(arena);

        AnimationTimer timer = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long l) {

                if (l - lastUpdate >= 28_000_000) {
                    onFrame();
                }
            }
        };

        setGameLoop(timer);

        center.setPadding(new Insets(20, 20, 20, 20));
        canvas = new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
        center.getChildren().addAll(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);

        loadImages(); //TODO if don't have image load

        center.setBackground(new Background(backgroundImage));

        bpGame.setCenter(center);

        bpGame.setTop(getToolBar());

        //TODO set right to be list view
        bpGame.setRight(listView(getArena()));

        start();

        return bpGame;
    }

    private void clearArena() {
        setArena(new DroneArena());
    }

    protected void save(DroneArena arena) {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        if (!getCurrentLoad().isEmpty()) {
            saveAndLoad.setFileName(getCurrentLoad());

            saveAndLoad.saveToFile(arena);

            System.out.println("Saved");
        } else {
            TextField textField = new TextField();
            Button save = new Button("Save name");

            newPopup(new HBox(textField, save));

            save.setOnAction(actionEvent -> {
                if (textField.getText() != null) {

                    saveAndLoad.setFileName(textField.getText());

                    saveAndLoad.saveToFile(arena);

                    System.out.println("Saved");
                }
            });
        }
    }

    private List<String> getFilesInDirectory() {
        List<String> saves = new ArrayList<>();
        File f = new File("."); // current directory
        File[] files = f.listFiles();
        for (File file : files) {
            if (!file.isDirectory() && file.getName().contains(".txt")) {
                saves.add(file.getName());
            }
        }
        return saves;
    }

    protected void load() {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        ListView listView = new ListView();

        for (String s : getFilesInDirectory()) {
            listView.getItems().add(s);
        }

        Button select = new Button("Open");

        select.setOnAction(actionEvent -> {
            DroneArenaSave d = saveAndLoad.loadFromFile(
                    listView.getSelectionModel().getSelectedItem().toString()
            );

            DroneArena arena = d.arenaLoad();

            setCurrentLoad(listView.getSelectionModel().getSelectedItem().toString());

            arena.logGame();

            reset();
            updateScene(arenaBuilder(arena));
            stage.setTitle(getCurrentLoad().replace(".txt", ""));

        });

        newPopup(new HBox(listView, select));
    }

    @Override
    public Pane createGame() {
        return createGame(new DroneArena());
    }

    private Pane arenaBuilder() {
        return arenaBuilder(new DroneArena());
    }

    private Pane arenaBuilder(DroneArena arena) {
        BorderPane builder = new BorderPane();

        Text title = new Text("Arena Creator");
        title.setFont(new Font("Juice ITC", 40));
        String s = " Size " + arena.getWidth() + "," + arena.getHeight();
        Text arenaDesc = new Text(s);
        arenaDesc.setFont(new Font("Juice ITC", 20));
        HBox hBox = new HBox(title, arenaDesc);


        Button drone1 = new Button("Drone 1");
        Button drone2 = new Button("Drone 2");
        Button static1 = new Button("Static 1");
        Button rotateAll = new Button("Rotate all random");


        drone1.setOnAction(actionEvent -> {
            arena.getObjectManager().addMovingObject(new MovingObject1());
            builder.setCenter(listView(arena));
        });

        drone2.setOnAction(actionEvent -> {
            arena.getObjectManager().addMovingObject(new MovingObject2());
            builder.setCenter(listView(arena));
        });


        static1.setOnAction(actionEvent -> {
            arena.getObjectManager().addStaticObject(new StaticObject1(), 200, 200);
            builder.setCenter(listView(arena));
        });

        rotateAll.setOnAction(actionEvent -> {
            arena.getObjectManager().moveRandom();
            builder.setCenter(listView(arena));
        });

        //TODO add better way to update list view

        Button start = new Button("Start current");
        start.setOnAction(actionEvent -> updateScene(createGame(arena)));
        VBox addObjects = new VBox(drone1, drone2, static1, rotateAll, start);
        addObjects.setSpacing(10);
        addObjects.setPadding(new Insets(10));


        builder.setTop(hBox);
        builder.setCenter(listView(arena));
        builder.setRight(addObjects);

        return builder;
    }

    @Override
    protected void onFrame() {
        getArena().updateGame(gc);
        bpGame.setRight(listView(getArena()));
    }

    private void loadImages() {
        Image image = new Image(Simulation2.class.getResourceAsStream("images/background.jpg"));

        backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    private Pane loadMenu() throws IOException {
        return FXMLLoader.load(getClass().getResource("menu.fxml"));
    }
}
