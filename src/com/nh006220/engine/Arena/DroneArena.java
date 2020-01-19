package com.nh006220.engine.Arena;

import com.nh006220.engine.ObjectTemplates.MovingObject;
import com.nh006220.engine.ObjectTemplates.StaticObject;
import com.nh006220.engine.SETTINGS;
import com.nh006220.simulator.Simulation;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Collections;

/**
 * simulation board where objects are stored
 */
public class DroneArena {
    /**
     * class path location where background image is stored
     */
    private final String backgroundLocation;
    /**
     * Size width and height of the arenas
     */
    private final int width;
    private final int height;
    /**
     * class used to manage storing game objects
     */
    private ObjectManager objectManager;
    /**
     * background object used to set the game background
     */
    private Background background;

    /**
     * default constructor
     * sets the size of the arena to be the size in settings
     * creates a new object manager
     * sets the background the be the default image
     */
    public DroneArena() {
        this.width = SETTINGS.CanvasWidth;
        this.height = SETTINGS.CanvasHeight;
        this.objectManager = new ObjectManager();
        this.backgroundLocation = "images/background.jpg";
        setBackground(backgroundLocation);
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * called on each frame
     * clears the game canvas
     * updates the position and checks for collision with each object in the object manager
     * draws object to the canvas
     *
     * @param gc graphics game is rendered to
     */
    public void updateGame(GraphicsContext gc) {
        gc.clearRect(0, 0, SETTINGS.CanvasWidth, SETTINGS.CanvasHeight);

        for (MovingObject m : objectManager.getMovingObjects()) {
            m.update();

            if (!m.getColliding() && m.isColliding(objectManager)) {
                m.onCollision();
            } else {
                m.move();
            }

            if (!m.isAlive()) getObjectManager().removeMoving(m);

            m.draw(gc);
        }

        for (StaticObject s : objectManager.getStaticObjects()) s.draw(gc);
    }

    /**
     * used to to debug
     */
    public void logGame() {
        System.out.println(objectManager.toString());
    }

    public Background getBackground() {
        return this.background;
    }

    /**
     * creates a background object from the class path
     *
     * @param image class path of image
     */
    public void setBackground(String image) {
        this.background = new Background(
                Collections.singletonList(new BackgroundFill(
                        Color.WHITE,
                        new CornerRadii(500),
                        new Insets(10))),
                Collections.singletonList(new BackgroundImage(
                        new Image(Simulation.class.getResourceAsStream(image), getWidth(), getWidth(), true, false),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT)));
    }

    public String getBackgroundLocation() {
        return backgroundLocation;
    }
}
