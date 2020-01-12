package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.GameWorld;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.engine.SerializablePoint2D;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.Objects.StaticObject1;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;


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
        Button openMenu = new Button("open menu");
        openMenu.setOnAction(actionEvent -> {
            try {
                updateScene(loadMenu());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox vBox = new VBox(newGame, openGame, settings, openMenu);

        Pane pane = new Pane(text, vBox);
        pane.setTranslateX(500);
        pane.setTranslateY(500);
        pane.setPadding(new Insets(300));

        bp.setTop(getToolBar());
        bp.setCenter(pane);

        return bp;
    }

    private Pane createGame(DroneArena arena) {
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

        loadImages(); //TODO if don't have image load

        center.setBackground(new Background(backgroundImage));

        bpGame.setCenter(center);

        bpGame.setTop(getToolBar());

        //TODO set right to be list view
        //bpGame.setRight(listView(getArena()));

        return bpGame;
    }

    protected void save(DroneArena arena) {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        saveAndLoad.saveToFile(arena);
    }

    protected void load() {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        DroneArenaSave d = saveAndLoad.loadFromFile();

        DroneArena arena = d.arenaLoad();

        arena.logGame();

        updateScene(createGame(arena));
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
        String s = " Size " + arena.getWidth() + "," + arena.getHeight();
        Text arenaDesc = new Text(s);
        arenaDesc.setFont(new Font("Juice ITC", 20));
        HBox hBox = new HBox(title, arenaDesc);


        Button drone1 = new Button("Drone 1");
        drone1.setOnAction(actionEvent -> {
            arena.getObjectManager().addMovingObject(new MovingObject1());
            builder.setCenter(listView(arena));
        });

        Button static1 = new Button("Static 1");
        static1.setOnAction(actionEvent -> {
            arena.getObjectManager().addStaticObject(new StaticObject1(), 30, 30);
            builder.setCenter(listView(arena));
        });

        Button start = new Button("Start current");
        start.setOnAction(actionEvent -> updateScene(createGame(arena)));
        VBox addObjects = new VBox(drone1, static1, start);


        builder.setTop(hBox);
        builder.setCenter(listView(arena));
        builder.setRight(addObjects);

        return builder;
    }

    private Node listView(DroneArena arena) {
        ListView arenaContent = new ListView();

        for (Object obj : arena.getObjectManager().getAllObjects()) {
            arenaContent.getItems().add(obj.toString());
        }

        Button editObject = new Button("set Object");
        editObject.setOnAction(actionEvent -> {
            int i = arenaContent.getSelectionModel().getSelectedIndex();
            newPopup(
                    setDroneMenu(
                            arena.getObjectManager().getAllObjects().get(i)
                    )
            );
        });

        arenaContent.getItems().add(editObject);

        return arenaContent;
    }

    public void newPopup(Node node) {
        BorderPane bp = new BorderPane();
        final Popup popup = new Popup();
        bp.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setPrefSize(300, 300);

        Button button = new Button("close");
        button.setOnAction(actionEvent -> {
            popup.hide();
        });
        bp.setBottom(new ToolBar(button));

        bp.setCenter(node);

        popup.getContent().add(bp);
        popup.show(stage);
    }

    private Pane setDroneMenu(Object i) {
        GridPane gridPane = new GridPane();

        Button setSpeed = new Button("Set speed 1-10");
        TextField setSpeed_input = new TextField();

        setSpeed.setOnAction(actionEvent -> {
            try {
                int increase = Integer.parseInt(setSpeed_input.getText());
                i.setVelocity((SerializablePoint2D) i.getVelocity().normalize().multiply(increase));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(setSpeed, 0, 0);
        gridPane.add(setSpeed_input, 0, 1);

        return gridPane;
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

    private Pane loadMenu() throws IOException {
        return FXMLLoader.load(getClass().getResource("menu.fxml"));
    }
}
