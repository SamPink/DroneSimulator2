package com.nh006220.engine.Objects;

import com.nh006220.engine.Arena.ObjectManager;
import com.nh006220.engine.SETTINGS;
import javafx.geometry.Point2D;


public abstract class MovingObject extends Object {
    private boolean colliding;

    public MovingObject(int width, int height) {
        super(width, height);
        this.setVelocity(new Point2D(1, 0));
    }


    public boolean getColliding() {
        return this.colliding;
    }

    public void setColliding(boolean b) {
        this.colliding = b;
    }

    public boolean isColliding(ObjectManager objectManager) {
        if (isInBounds(SETTINGS.CanvasWidth, SETTINGS.CanvasWidth)) {
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
    private boolean isInBounds(int height, int width) {
        return (getX() + getWidth()) > width || (getX() - getWidth()) < 0
                || (getY() + getHeight()) > height || (getY() - getHeight()) < 0;
    }

    public void onCollision() {
        rotateAngle(getRotate() + 90);
        setColliding(false);
    }
}
