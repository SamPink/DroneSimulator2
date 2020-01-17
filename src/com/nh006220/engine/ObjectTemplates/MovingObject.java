package com.nh006220.engine.ObjectTemplates;

import com.nh006220.engine.Arena.ObjectManager;
import com.nh006220.simulator.SETTINGS;
import javafx.geometry.Point2D;
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
    private CollisionType collisionType;


    public MovingObject(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        super(w, h, xVel, yVel, image, droneType);
        //setVelocity(new SerializablePoint2D(2.5, 0));
        moveRandom();
        health = 100;
        hitBox = new Rectangle(getWidth() * hitBoxRange, getHeight() * hitBoxRange);
        setMoving(true);
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
        if (isAlive()) {
            super.draw(gc);
            if (drawHitBox = true) {
                gc.setFill(Color.BLUE);
                gc.setStroke(Color.RED);
                gc.strokeRect(getX(), getY(), hitBox.getWidth(), hitBox.getWidth());
            }
        }
    }

    /**
     * sets the position of the shape to be its current position + velocity
     * called every frame
     */
    @Override
    public void update() {
        if (isAlive() || isInBounds(SETTINGS.CanvasWidth, SETTINGS.CanvasHeight)) {
            super.update();
            hitBox.setTranslateX(getX());
            hitBox.setTranslateY(getY());
        }
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
        } else {

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
                    setCollisionType(CollisionType.Object);
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
        if ((getX() + getHitBox().getWidth()) > width) {
            setCollisionType(CollisionType.Right);
            return true;
        }
        if ((getX() - getHitBox().getWidth()) < 0) {
            setCollisionType(CollisionType.Left);
            return true;
        }
        if ((getY() + getHitBox().getHeight()) > height) {
            setCollisionType(CollisionType.Bottom);
            return true;
        }
        if ((getY() - getHitBox().getHeight()) < 0) {
            setCollisionType(CollisionType.Top);
            return true;
        }
        return false;
    }

    public void onCollision() {
        toString();
        Random random = new Random();
        if (getCollisionType() == CollisionType.Object) {
            if (getCollidingWith().isMoving()) {
                Point2D velocity = getVelocity();
                setVelocity(getCollidingWith().getVelocity());
                getCollidingWith().setVelocity(velocity);
                setVelMultiply(getVelMultiply() + 0.01);
                setHealth(getHealth() - 1);
            } else {
                rotateAngle((int) (((getRotate() + Math.PI)) % (2 * Math.PI)));
                setVelMultiply(1.1);
            }
        } else if (getCollisionType() == CollisionType.Left) {
            //left wall opposite direction
            rotateAngle((int) (((getRotate() + Math.PI)) % (2 * Math.PI)));
        } else if (getCollisionType() == CollisionType.Right) {
            rotateAngle(-180 - random.nextInt(90));
        } else if (getCollisionType() == CollisionType.Top) {
            rotateAngle(90 + random.nextInt(90));
        } else if (getCollisionType() == CollisionType.Bottom) {
            rotateAngle(-90 - random.nextInt(90));
        }

        setColliding(false);
        setCollidingWith(null);
        setCollisionType(null);

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

    public boolean isAlive() {
        return getHealth() > 0;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

}

