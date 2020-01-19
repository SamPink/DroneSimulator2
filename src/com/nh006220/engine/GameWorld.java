package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.MovingObject;
import com.nh006220.engine.ObjectTemplates.Object;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * base class that application can be build from
 */
public abstract class GameWorld extends Application {
    /**
     * the top level element of the game
     */
    private Stage stage;

    /**
     * title of the main window
     */
    private String title;

    /**
     * name of current loaded file
     */
    private String currentLoad = "";

    /**
     * running game timer
     */
    private AnimationTimer timer;

    /**
     * stores game arena
     */
    private DroneArena arena;

    /**
     * where game graphics a drawn to
     */
    private GraphicsContext gc;

    /**
     * canvas game is drawn on
     */
    private Canvas canvas;


    /**
     * Creates initial scene
     * by default will open new Menu
     *
     * @param stage root element of the game
     */
    protected void initialize(Stage stage) {
        setStage(stage);
        stage.setTitle(getTitle());
        stage.setResizable(false);

        setScene(newMenu());

        stage.show();
    }

    /**
     * Creates new menu pane
     *
     * @return menu pane
     */
    protected abstract Pane newMenu();

    /**
     * Creates game pane
     *
     * @return game pane
     */
    protected abstract Pane newGame(DroneArena arena);

    /**
     * Creates new help pane
     *
     * @return help pane
     */
    protected abstract Pane newHelp();

    /**
     * creates a new arena builder
     *
     * @return arena builder
     */
    protected abstract Pane newArenaBuilder(DroneArena arena);

    /**
     * creates new toolbar
     *
     * @return toolbar
     */
    protected abstract ToolBar newToolbar();

    /**
     * creates a new popup window to add game options to
     *
     * @param node element to display in center of popup
     * @return popup element
     */
    protected final Popup newPopup(Node node) {
        pause();

        BorderPane bp = new BorderPane();
        Popup popup = new Popup();

        bp.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setPrefSize(300, 300);

        Button button = new Button("close");
        button.setOnAction(actionEvent -> {
            popup.hide();
            start();
        });
        bp.setBottom(new ToolBar(button));

        bp.setCenter(node);

        popup.getContent().add(bp);

        popup.show(stage);

        return popup;
    }

    /**
     * list view of arena contents
     * of click of each object will open a object editor
     *
     * @param a arena to show content of
     * @return list view node with arena contents
     */
    protected Node newListView(DroneArena a) {
        ListView<String> arenaContent = new ListView<>();
        arenaContent.setMinWidth(400);

        for (Object obj : a.getObjectManager().getAllObjects()) {
            arenaContent.getItems().add(obj.toString());
        }

        arenaContent.setOnMousePressed(mouseEvent -> {
            int i = arenaContent.getSelectionModel().getSelectedIndex();

            newPopup(
                    newDroneMenu(a.getObjectManager().getAllObjects().get(i))
            );
        });

        return arenaContent;
    }

    /**
     * drone editor screen
     * allows the properties of the object to edited
     *
     * @param i object to edit
     * @return popup pane
     */
    private Pane newDroneMenu(Object i) {
        Slider speed = new Slider(0, 10, 1);
        speed.setValue(i.getVelMultiply());
        speed.setShowTickLabels(true);

        HBox setSpeed = new HBox(new Text("Set speed"), speed);

        Slider rotation = new Slider(0, 360, 5);
        rotation.setValue(i.getRotate());
        rotation.setShowTickLabels(true);

        HBox setRotation = new HBox(new Text("Set rotation"), rotation);

        Slider posX = new Slider(0, SETTINGS.CanvasWidth, 5);
        posX.setShowTickLabels(true);
        posX.setValue(i.getX());
        posX.onDragDetectedProperty().addListener(observable -> {

            System.out.println("hey");
            i.setX((int) posX.getValue());
            i.draw(getGc());
        });
        Slider posY = new Slider(0, SETTINGS.CanvasHeight, 5);
        posY.onDragDetectedProperty().addListener(observable -> {
            i.setY((int) posY.getValue());
            i.draw(getGc());
        });
        posY.setShowTickLabels(true);
        posY.setValue(i.getY());

        HBox setX = new HBox(new Text("Set x"), posX);
        HBox setY = new HBox(new Text("Set y"), posY);

        Slider width = new Slider(0, 100, 5);
        width.setShowTickLabels(true);
        width.setValue(i.getWidth());

        width.onDragDetectedProperty().addListener(observable -> {
            i.setWidth((int) width.getValue());
            i.draw(getGc());
        });

        Slider height = new Slider(0, 100, 5);
        height.setShowTickLabels(true);
        height.setValue(i.getHeight());

        height.onDragDetectedProperty().addListener(observable -> {
            i.setHeight((int) height.getValue());
            i.draw(getGc());
        });

        HBox setWidth = new HBox(new Text("Set width"), posX);
        HBox setHeight = new HBox(new Text("Set height"), posY);

        Button store = new Button("save");

        store.setOnAction(actionEvent -> {
            i.setVelMultiply((int) speed.getValue());
            i.rotateAngle((int) rotation.getValue());
            i.setPos((int) posX.getValue(), (int) posY.getValue());
            i.setWidth((int) width.getValue());
            i.setHeight((int) height.getValue());
            System.out.println(i.toString());
        });

        Button setPosOnScreenButton = new Button("Set position on canvas");
        setPosOnScreenButton.setOnAction(actionEvent -> getCanvas().setOnMousePressed(mouseEvent -> {
            i.setPos((int) mouseEvent.getX(), (int) mouseEvent.getY());
            i.update();
            getGc().fillRect(mouseEvent.getX(), mouseEvent.getY(), 5, 5);
            getCanvas().setOnMousePressed(mouseEvent1 -> System.out.println());
        }));

        Button remove = new Button("Remove");

        remove.setOnAction(actionEvent -> {
            if (i.isMoving()) getArena().getObjectManager().removeMoving((MovingObject) i);
            else getArena().getObjectManager().removeStatic(i);
        });


        return new VBox(setSpeed, setRotation, setX, setY, store, setPosOnScreenButton, setWidth, setHeight, remove);
    }

    /**
     * create new game timer
     */
    protected final void newTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                onFrame();
                //TODO onSecond()
            }
        };

        setTimer(timer);
    }

    /**
     * starts the game timer if running
     */
    protected void start() {
        try {
            getTimer().start();
        } catch (Exception e) {
            System.out.println("Cant start timer");
        }
    }

    /**
     * pauses game timer if running
     */
    protected void pause() {
        try {
            getTimer().stop();
        } catch (Exception e) {
            System.out.println("Cant pause");
        }
    }

    /**
     * method called on each tick of animation timer
     */
    protected abstract void onFrame();

    /**
     * method called each second
     */
    protected abstract void onSecond();

    /**
     * saves passed drone arena to text file
     *
     * @param arena arena to save
     */
    protected void save(DroneArena arena) {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        if (!getCurrentLoad().isEmpty()) {
            //Allows current arena to be auto saved if it is loaded from a save
            saveAndLoad.setFileName(getCurrentLoad());

            saveAndLoad.saveToFile(arena);

            System.out.println("Saved");
        } else {
            //Create a new save
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

    /**
     * opens popup asking what arena to load
     * builds a drone arena from file
     */
    protected void load() {
        SaveAndLoad saveAndLoad = new SaveAndLoad();

        ListView<String> listView = new ListView<>();

        for (String s : getFilesInDirectory()) {
            listView.getItems().add(s);
        }

        Button select = new Button("Open");

        select.setOnAction(actionEvent -> {
            DroneArenaSave d = saveAndLoad.loadFromFile(
                    listView.getSelectionModel().getSelectedItem()
            );

            DroneArena arena = d.arenaLoad();

            setCurrentLoad(listView.getSelectionModel().getSelectedItem());

            arena.logGame();

            //reset();
            setScene(newArenaBuilder(arena));
            stage.setTitle(getCurrentLoad().replace(".txt", ""));

        });

        newPopup(new HBox(listView, select));
    }


    /**
     * gets text files in working directory
     *
     * @return game saves
     */
    private List<String> getFilesInDirectory() {
        List<String> saves = new ArrayList<>();
        File f = new File("."); // current directory
        File[] files = f.listFiles();
        assert files != null;
        for (File file : files) {
            if (!file.isDirectory() && file.getName().contains(".txt")) {
                saves.add(file.getName());
            }
        }
        return saves;
    }

    /**
     * turns image into background image
     *
     * @param s location of image
     * @return background image
     */
    protected BackgroundImage createBackgroundImage(String s) {
        Image image = new Image(getClass().getResourceAsStream(s));

        return new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }

    /**
     * set root level pane
     * used to move between scenes
     * all scenes in the game have the same size window
     */
    protected void setScene(Pane pane) {
        getStage().setScene(new Scene(pane, SETTINGS.SceneWidth, SETTINGS.SceneHeight));
    }

    protected DroneArena getArena() {
        return this.arena;
    }

    protected void setArena(DroneArena arena) {
        this.arena = arena;
    }

    private String getTitle() {
        return title;
    }

    private Stage getStage() {
        return stage;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    private String getCurrentLoad() {
        return currentLoad;
    }

    private void setCurrentLoad(String currentLoad) {
        this.currentLoad = currentLoad;
    }

    private AnimationTimer getTimer() {
        return timer;
    }

    private void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }

    protected GraphicsContext getGc() {
        return gc;
    }

    protected void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    protected void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * closes the game
     * saves first
     *
     * @throws Exception if cant close
     */
    @Override
    public void stop() throws Exception {
        save(getArena());
        super.stop();
    }
}
