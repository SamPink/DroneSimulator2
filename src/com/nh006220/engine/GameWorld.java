package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.simulator.SETTINGS;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


/**
 * This application demonstrates a JavaFX 2.x Game Loop.
 * Shown below are the methods which comprise of the fundamentals to a
 * simple game loop in JavaFX:
 * <pre>
 *  <b>initialize()</b> - Initialize the game world.
 *  <b>beginGameLoop()</b> - Creates a JavaFX Timeline object containing the game life cycle.
 *  <b>updateSprites()</b> - Updates the sprite objects each period (per frame)
 *  <b>checkCollisions()</b> - Method will determine objects that collide with each other.
 *  <b>cleanupSprites()</b> - Any sprite objects needing to be removed from play.
 * </pre>
 *
 * @author cdea
 */
public abstract class GameWorld {

    /**
     * The game loop using JavaFX's AnimationTimer class.
     */
    private static AnimationTimer timer;
    /**
     * Number of frames per second.
     */
    private final int framesPerSecond;
    /**
     * Title in the application window.
     */
    private final String windowTitle;
    /**
     * The JavaFX Scene as the game surface
     */
    private Scene surface;
    /**
     * All nodes to be displayed in the game window.
     */
    private Pane scene;
    /**
     * The drone manager.
     */
    private DroneArena arena;

    protected Stage stage;
    protected Pane center = new Pane();
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected BackgroundImage backgroundImage = null;
    protected BorderPane bpGame;
    private String currentLoad;

    public String getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(String currentLoad) {
        this.currentLoad = currentLoad;
    }

    public BorderPane getBpGame() {
        return bpGame;
    }

    public void setBpGame(BorderPane bpGame) {
        this.bpGame = bpGame;
    }

    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     *
     * @param fps   - Frames per second.
     * @param title - Title of the application window.
     */
    public GameWorld(final String title, int fps) {
        framesPerSecond = fps;
        windowTitle = title;
        // create and set timeline for the game loop
    }

    /**
     * The game loop (Timeline) which is used to update, check collisions, and
     * cleanup sprite objects at every interval (fps).
     *
     * @return Timeline An animation running indefinitely representing the game
     * loop.
     */
    protected static AnimationTimer getTimer() {
        return timer;
    }

    /**
     * The sets the current game loop for this game world.
     *
     * @param timer Timeline object of an animation running indefinitely
     *              representing the game loop.
     */
    protected static void setGameLoop(AnimationTimer timer) {
        GameWorld.timer = timer;
    }

    /**
     * Initialize the game world by update the JavaFX Stage.
     *
     * @param primaryStage The main window containing the JavaFX Scene.
     */
    public abstract void initialize(final Stage primaryStage);

    public abstract Pane createMenu();

    public abstract Pane createGame();

    protected abstract void onFrame();

    /**
     * Returns the frames per second.
     *
     * @return int The frames per second.
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Returns the game's window title.
     *
     * @return String The game's window title.
     */
    public String getWindowTitle() {
        return windowTitle;
    }

    /**
     * Returns the sprite manager containing the sprite objects to
     * manipulate in the game.
     *
     * @return SpriteManager The sprite manager.
     */
    public DroneArena getArena() {
        return arena;
    }

    protected Object getObject(int i) {
        return arena.getObjectManager().getAllObjects().get(i);
    }

    public void setArena(DroneArena arena) {
        this.arena = arena;
    }

    /**
     * Returns the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @return Scene The JavaFX scene graph.
     */
    public Scene getGameSurface() {
        return surface;
    }

    /**
     * Sets the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setGameSurface(Scene gameSurface) {
        this.surface = gameSurface;
    }

    /**
     * All JavaFX nodes which are rendered onto the game surface(Scene) is
     * a JavaFX Group object.
     *
     * @return Group The root containing many child nodes to be displayed into
     * the Scene area.
     */
    public Pane getScene() {
        return scene;
    }

    protected void newPopup(Node node) {
        pause();
        BorderPane bp = new BorderPane();
        final Popup popup = new Popup();
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
    }

    protected Node listView(DroneArena arena) {
        ListView arenaContent = new ListView();
        arenaContent.setMinWidth(400);

        for (Object obj : arena.getObjectManager().getAllObjects()) {
            arenaContent.getItems().add(obj.toString());
        }

        arenaContent.setOnMousePressed(mouseEvent -> {
            int i = arenaContent.getSelectionModel().getSelectedIndex();

            newPopup(
                    setDroneMenu(arena.getObjectManager().getAllObjects().get(i))
            );
        });

        return arenaContent;
    }

    protected Pane setDroneMenu(Object i) {
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
        Slider posY = new Slider(0, SETTINGS.CanvasHeight, 5);
        posY.setShowTickLabels(true);
        posY.setValue(i.getY());

        HBox setX = new HBox(new Text("Set x"), posX);
        HBox setY = new HBox(new Text("Set y"), posY);

        Button store = new Button("save");

        store.setOnAction(actionEvent -> {
            i.setVelMultiply((int) speed.getValue());
            i.rotateAngle((int) rotation.getValue());
            i.setPos((int) posX.getValue(), (int) posY.getValue());

            System.out.println(i.toString());
        });
        return new VBox(setSpeed, setRotation, setX, setY, store);
    }

    protected ToolBar getToolBar() {
        Button start = new Button("Start");
        Button pause = new Button("Pause");
        Button stop = new Button("Stop");
        Button save = new Button("Save");
        Button load = new Button("Load");
        Button resetArena = new Button("Reset arena");
        Button arenaEditor = new Button("Edit Arena");


        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(DroneType.values()));

        start.setOnAction(actionEvent -> start());
        pause.setOnAction(actionEvent -> pause());
        stop.setOnAction(actionEvent -> shutdown());
        save.setOnAction(actionEvent -> save(arena));
        load.setOnAction(actionEvent -> load());
        resetArena.setOnAction(actionEvent -> reset());
        comboBox.setOnAction(actionEvent -> {
            spawn(DroneType.valueOf(comboBox.getValue().toString()));
        });
        arenaEditor.setOnAction(actionEvent -> {
            newPopup(listView(getArena()));
        });


        return new ToolBar(start, pause, stop, comboBox, save, load, resetArena, arenaEditor);
    }

    protected void reset() {
        try {
            gc.clearRect(0, 0, SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);
            setArena(new DroneArena());
            getArena().updateGame(gc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected abstract void load();

    protected abstract void save(DroneArena arena);


    /**
     * Sets the JavaFX Group that will hold all JavaFX nodes which are rendered
     * onto the game surface(Scene) is a JavaFX Group object.
     *
     * @param scene The root container having many children nodes
     *              to be displayed into the Scene area.
     */
    protected void setSceneNodes(Pane scene) {
        this.scene = scene;
    }

    protected void updateScene(Pane scene) {
        setSceneNodes(scene);
        setGameSurface(new Scene(getScene(), SETTINGS.SceneWidth, SETTINGS.SceneHeight));
        stage.setScene(getGameSurface());
    }

    protected void start() {
        try {
            getTimer().start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Stop threads and stop media from playing.
     */
    protected void shutdown() {
        getTimer().stop();
        updateScene(createMenu());
    }

    protected void pause() {
        try {
            getTimer().stop();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    protected void spawn(DroneType droneType) {
        getArena().getObjectManager().addObject(droneType);
    }

    protected void spawn() {

    }
}