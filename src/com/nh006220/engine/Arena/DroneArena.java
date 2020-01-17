package com.nh006220.engine.Arena;

import com.nh006220.engine.ObjectTemplates.MovingObject;
import com.nh006220.engine.ObjectTemplates.StaticObject;
import com.nh006220.engine.SETTINGS;
import javafx.scene.canvas.GraphicsContext;

public class DroneArena {
    private DroneActions droneActions;
    private int width, height;
    private ObjectManager objectManager;

    public DroneArena() {
        this.width = SETTINGS.CanvasWidth;
        this.height = SETTINGS.CanvasHeight;
        this.objectManager = new ObjectManager();
        this.droneActions = new DroneActions();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public DroneActions getDroneActions() {
        return droneActions;
    }

    public void setDroneActions(DroneActions droneActions) {
        this.droneActions = droneActions;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

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

    public void logGame() {
        System.out.println(objectManager.toString());
    }

    public void rotateRandom() {
        droneActions.rotateRandom(objectManager);
    }
}
