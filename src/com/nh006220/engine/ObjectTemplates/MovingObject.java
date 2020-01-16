package com.nh006220.engine.ObjectTemplates;

import com.nh006220.engine.Arena.ObjectManager;
import com.nh006220.simulator.SETTINGS;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public abstract class MovingObject extends Object {
    private boolean colliding;
    private int health;
    private Object collidingWith;
    private Rectangle hitBox;
    private double hitBoxRange = 1;
    private boolean drawHitBox;


    public MovingObject(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        super(w, h, xVel, yVel, image, droneType);
        //setVelocity(new SerializablePoint2D(2.5, 0));
        moveRandom();
        health = 100;
        hitBox = new Rectangle(getWidth() * hitBoxRange, getHeight() * hitBoxRange);
    }

    public boolean isColliding() {
        return colliding;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public double getHitBoxRange() {
        return hitBoxRange;
    }

    public void setHitBoxRange(double hitBoxRange) {
        this.hitBoxRange = hitBoxRange;
        hitBox = new Rectangle(getWidth() * hitBoxRange, getHeight() * hitBoxRange);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @param gc graphics context of the canvas to draw to
     */
    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        if (drawHitBox = true) {
            gc.setFill(Color.BLUE);
            gc.setStroke(Color.RED);
            gc.strokeRect(getX(), getY(), hitBox.getWidth(), hitBox.getWidth());
        }
    }

    /**
     * sets the position of the shape to be its current position + velocity
     * called every frame
     */
    @Override
    public void update() {
        super.update();
        hitBox.setTranslateX(getX());
        hitBox.setTranslateY(getY());
    }

    private void moveRandom() {
        Random random = new Random();

        rotateAngle(random.nextInt(360));
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
        for (Object object : objectManager.getAllObjects()) {
            if (!this.equals(object)) {
                if (getHitBox().getBoundsInParent().intersects(object.getRectangle().getBoundsInParent())) {
                    setColliding(true);
                    setCollidingWith(object);
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
        if ((getX() + getHitBox().getWidth()) > width)
            return true;
        if ((getX() - getHitBox().getWidth()) < 0)
            return true;
        if ((getY() + getHitBox().getHeight()) > height)
            return true;
        return (getY() - getHitBox().getHeight()) < 0;
    }

    public void onCollision() {
        if (getCollidingWith() == null) {
            rotateAngle(getRotate() + 90);
        } else {
            int rotate = getRotate();
            rotateAngle(getCollidingWith().getRotate());
            getCollidingWith().rotateAngle(rotate);
        }

        setColliding(false);
        setCollidingWith(null);
    }

    public Object getCollidingWith() {
        return collidingWith;
    }

    public void setCollidingWith(Object obj) {
        this.collidingWith = obj;
    }

    public boolean isDrawHitBox() {
        return drawHitBox;
    }

    public void setDrawHitBox(boolean drawHitBox) {
        this.drawHitBox = drawHitBox;
    }
}
