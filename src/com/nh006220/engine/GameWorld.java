package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.SETTINGS;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
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

    protected ToolBar getToolBar() {
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button addDrone = new Button("add Drone");

        start.setOnAction(actionEvent -> start());
        stop.setOnAction(actionEvent -> shutdown());
        addDrone.setOnAction(actionEvent -> spawn());

        return new ToolBar(start, stop, addDrone);
    }

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
        getTimer().start();
    }

    /**
     * Stop threads and stop media from playing.
     */
    public void shutdown() {
        getTimer().stop();
        updateScene(createMenu());
    }

    protected void spawn() {
        getArena().getObjectManager().addMovingObject(new MovingObject1());
    }
}