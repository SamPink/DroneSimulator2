package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.GameWorld;
import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.SETTINGS;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Simulation extends GameWorld {
    private BorderPane bpGame;
    private GraphicsContext gc;

    @Override
    protected Pane newMenu() {
        BorderPane bp = new BorderPane();
        Text text = new Text("Drone Simulator");

        text.setFont(new Font("Juice ITC", 93));

        Button newGame = new Button("New Game");
        Button openGame = new Button("Open Game");
        Button simulation1 = new Button("Simulation 1");
        Button settings = new Button("Settings");
        Button openHelp = new Button("Open Help");

        newGame.setOnAction(actionEvent -> setScene(newArenaBuilder(new DroneArena())));
        openGame.setOnAction(actionEvent -> load());
        openHelp.setOnAction(actionEvent -> setScene(newHelp()));
        simulation1.setOnAction(actionEvent -> setScene(newGame(SimulationBuilder.basicArena())));

        VBox vBox = new VBox(newGame, openGame, simulation1, settings, openHelp);

        vBox.setPadding(new Insets(20));

        Pane pane = new Pane(text, vBox);
        pane.setTranslateX(500);
        pane.setTranslateY(500);
        pane.setPadding(new Insets(300));

        //bp.setTop(newToolbar());
        bp.setCenter(pane);

        return bp;
    }

    /**
     * Creates game pane
     *
     * @param arena
     * @return game pane
     */
    @Override
    protected Pane newGame(DroneArena arena) {
        bpGame = new BorderPane();
        Pane pane = new Pane();

        setArena(arena);

        newTimer();

        setCanvas(new Canvas(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight));

        setGc(getCanvas().getGraphicsContext2D());

        getGc().clearRect(0, 0, SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);

        Canvas background = new Canvas();
        background.getGraphicsContext2D().setFill(Color.BLUE);

        pane.getChildren().add(background);
        pane.getChildren().add(getCanvas());

        background.toFront();

        bpGame.setCenter(pane);

        bpGame.setTop(newToolbar());

        bpGame.setRight(newListView(getArena()));

        start();

        return bpGame;
    }

    /**
     * Creates new help pane
     *
     * @return help pane
     */
    @Override
    protected Pane newHelp() {
        return null;
    }


    /**
     * creates a new arena builder
     *
     * @return arena builder
     */
    @Override
    protected Pane newArenaBuilder(DroneArena arena) {
        BorderPane builder = new BorderPane();

        Text title = new Text("Arena Creator");
        title.setFont(new Font("Juice ITC", 40));
        String s = " Size " + arena.getWidth() + "," + arena.getHeight();
        Text arenaDesc = new Text(s);
        arenaDesc.setFont(new Font("Juice ITC", 20));
        HBox hBox = new HBox(title, arenaDesc);

        VBox addObjects = new VBox();

        for (DroneType d : DroneType.values()) {
            Button b = new Button(d.toString());
            b.setOnAction(actionEvent -> {
                arena.getObjectManager().addObject(d);
                builder.setCenter(newListView(arena));
            });
            addObjects.getChildren().add(b);
        }

        Button rotateAll = new Button("Rotate all random");

        rotateAll.setOnAction(actionEvent -> {
            arena.getObjectManager().moveRandom();
            builder.setCenter(newListView(arena));
        });

        //TODO add better way to update list view

        Button start = new Button("Start current");
        start.setOnAction(actionEvent -> setScene(newGame(arena)));

        addObjects.getChildren().addAll(rotateAll, start);
        addObjects.setSpacing(10);
        addObjects.setPadding(new Insets(10));


        builder.setTop(hBox);
        builder.setCenter(newListView(arena));
        builder.setRight(addObjects);

        return builder;
    }

    /**
     * creates new toolbar
     *
     * @return toolbar
     */
    @Override
    protected ToolBar newToolbar() {
        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button stop = new Button("Stop");
        Button save = new Button("Save");
        Button load = new Button("Load");
        Button resetArena = new Button("Reset arena");
        Button arenaEditor = new Button("Edit Arena");


        ComboBox<? extends DroneType> comboBox = new ComboBox<>(FXCollections.observableArrayList(DroneType.values()));


        start.setOnAction(actionEvent -> start());
        pause.setOnAction(actionEvent -> pause());
        stop.setOnAction(actionEvent -> pause());
        save.setOnAction(actionEvent -> save(getArena()));
        load.setOnAction(actionEvent -> load());
        resetArena.setOnAction(actionEvent -> reset());
        comboBox.setOnAction(actionEvent -> {
            DroneType droneType = DroneType.valueOf(comboBox.getValue().toString());
            getArena().getObjectManager().addObject(droneType);
        });
        arenaEditor.setOnAction(actionEvent -> {
            newPopup(newListView(getArena()));
        });


        return new ToolBar(start, pause, stop, comboBox, save, load, resetArena, arenaEditor);
    }

    private void reset() {
        setScene(newGame(new DroneArena()));
    }

    @Override
    protected void onFrame() {
        getArena().updateGame(getGc());

        bpGame.setRight(newListView(getArena()));
    }

    @Override
    protected void onSecond() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        initialize(stage);
    }
}
