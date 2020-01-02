package com.nh006220.engine.Arena;

import com.nh006220.engine.Objects.MovingObject;
import com.nh006220.engine.Objects.StaticObject;
import javafx.scene.canvas.GraphicsContext;

public class DroneArena {
    private int width, height;
    private ObjectManager objectManager;

    public DroneArena(int width, int height) {
        this.width = width;
        this.height = height;

        objectManager = new ObjectManager();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateGame(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 800);

        for (MovingObject m : objectManager.getMovingObjects()) {
            m.update();
            if (m.isColliding(objectManager)) {
                m.onCollision();
            }
            m.draw(gc);
        }

        //TODO don't draw these every frame, maybe draw every time something happens to them
        for (StaticObject s : objectManager.getStaticObjects()) {
            s.update();
            s.draw(gc);
        }
    }

    public void logGame() {
        System.out.println(objectManager.toString());
    }
}