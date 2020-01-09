package com.nh006220.engine.ObjectTemplates;

import com.nh006220.engine.Arena.ObjectManager;
import com.nh006220.simulator.SETTINGS;
import javafx.geometry.Point2D;


public abstract class MovingObject extends Object {
    private boolean colliding;

    public MovingObject(int width, int height) {
        super(width, height);
        setVelocity(new Point2D(2.5, 0));
    }


    public boolean getColliding() {
        return this.colliding;
    }

    public void setColliding(boolean b) {
        this.colliding = b;
    }

    public boolean isColliding(ObjectManager objectManager) {
        if (isInBounds(SETTINGS.CanvasWidth, SETTINGS.GroundOnBackgroundImage)) {
            setColliding(true);
        } else if (isCollidingWithObject(objectManager)) {
            setColliding(true);
        }
        return getColliding();
    }

    /**
     * loop through all objects in arena
     *
     * @param objectManager
     * @return true if intersecting
     */
    private boolean isCollidingWithObject(ObjectManager objectManager) {
        for (MovingObject m : objectManager.getMovingObjects()) {
            if (!this.equals(m)) {
                if (getRectangle().getBoundsInParent().intersects(m.getRectangle().getBoundsInParent())) {
                    setColliding(true);
                }
            }
        }

        return getColliding();
    }

    /**
     * @param height of arena
     * @param width  of arena
     * @return true if drone object is in arena else false
     */
    private boolean isInBounds(int width, int height) {
        if ((getX() + getWidth()) > width)
            return true;
        if ((getX() - getWidth()) < 0)
            return true;
        if ((getY() + getHeight()) > height)
            return true;
        return (getY() - getHeight()) < 0;
    }

    public void onCollision() {
        rotateAngle(getRotate() + 90);
        setColliding(false);
    }
}
