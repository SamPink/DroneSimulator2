package com.nh006220.engine.ObjectTemplates;

import com.nh006220.engine.Arena.ObjectManager;
import com.nh006220.engine.SETTINGS;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;


/**
 * implements object
 * parent class all moving objects inherit from
 * has a hit box rectangle used to determine collisions
 * only drawn and updated if alive (health > 0)
 */
public abstract class MovingObject extends Object {
    /**
     * true if object colliding else false
     */
    private boolean colliding;
    /**
     * health value, if < 0 object !alive
     */
    private int health;
    /**
     * stores the colliding with object
     */
    private Object collidingWith;
    /**
     * hitBox used for collisions
     */
    private Rectangle hitBox;
    /**
     * size scale for hitBox so can be larger or smaller than object
     */
    private double hitBoxRange = 1;
    /**
     * if the hitBox should be rendered to the screen
     */
    private boolean drawHitBox;
    /**
     * stores the type of collisions the object is having
     */
    private CollisionType collisionType;
    /**
     * color of the hitBox when render to the screen
     */
    private Color hitBoxColor;
    /**
     * determines when object should change direction
     */
    private int changeDirection;
    /**
     * velocity multiplier scale
     */
    private double velMultiply = 1;


    /**
     * moving object constructor, calls super constructor
     *
     * @param w         width of object
     * @param h         height of object
     * @param xVel      x speed  of object
     * @param yVel      y speed of object
     * @param image     class path image location
     * @param droneType enum type
     */
    protected MovingObject(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        super(w, h, xVel, yVel, image, droneType);
        health = 100;
        hitBox = new Rectangle(getWidth() * hitBoxRange, getHeight() * hitBoxRange);
        setMoving();
    }

    public boolean isColliding() {
        return colliding;
    }

    private Rectangle getHitBox() {
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

    private int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
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
                gc.setStroke(getHealthColor());
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
        if (isAlive() || isInBounds(SETTINGS.CanvasHeight)) {
            super.update();
            hitBox.setTranslateX(getX());
            hitBox.setTranslateY(getY());
            hitBoxColor = getHealthColor();
        }
    }

    public boolean getColliding() {
        return this.colliding;
    }

    private void setColliding(boolean b) {
        this.colliding = b;
    }

    /**
     * is object colliding with anything within the arena
     *
     * @param objectManager store of objects that can be colliding with
     * @return true if colliding, else false
     */
    public boolean isColliding(ObjectManager objectManager) {
        if (isInBounds(SETTINGS.GroundOnBackgroundImage) || isCollidingWithObject(objectManager))
            setColliding(true);

        return getColliding();
    }

    /**
     * loop through all objects in arena
     * tests each object to see if it intersects with hitBox
     *
     * @param objectManager all objects in arena
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
     * @return true if drone object is in arena else false
     */
    private boolean isInBounds(int height) {
        if ((getX() + getHitBox().getWidth()) > SETTINGS.CanvasWidth) {
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

    /**
     * action the object should take of is colliding
     * uses collision type to determine what type of action should be taken
     * sets colliding to false
     */
    public void onCollision() {
        Random random = new Random();
        if (getCollisionType() == CollisionType.Object) {
            if (getCollidingWith().isMoving()) {
                //colliding with a moving object, switch velocity with colliding with object
                //TODO switch direction not speed
                Point2D velocity = getVelocity();
                setVelocity(getCollidingWith().getVelocity());
                getCollidingWith().setVelocity(velocity);
                setVelMultiply(getVelMultiply() + 0.01);
                setHealth(getHealth() - 5);
            } else {
                //colliding with static object
                rotateAngle(getRotate() + 50);
            }
        } else if (getCollisionType() == CollisionType.Left) {
            //left wall opposite direction
            rotateAngle((int) (((getRotate() + Math.PI)) % (2 * Math.PI)));
        } else if (getCollisionType() == CollisionType.Right) {
            //right wall
            rotateAngle(-180 - random.nextInt(90));
        } else if (getCollisionType() == CollisionType.Top) {
            //top wall
            rotateAngle(90 + random.nextInt(90));
        } else if (getCollisionType() == CollisionType.Bottom) {
            //bottom wall
            rotateAngle(-90 - random.nextInt(90));
        }

        setColliding(false);
        setCollidingWith(null);
        setCollisionType(null);

    }

    private Object getCollidingWith() {
        return collidingWith;
    }

    private void setCollidingWith(Object obj) {
        this.collidingWith = obj;
    }

    public boolean isDrawHitBox() {
        return drawHitBox;
    }

    protected void setDrawHitBox() {
        this.drawHitBox = false;
    }

    public boolean isAlive() {
        return getHealth() > 0;
    }

    private CollisionType getCollisionType() {
        return collisionType;
    }

    private void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    private Color getHealthColor() {
        int i = 255 - 2 * (100 - getHealth());
        if (i > 255) i = 255;
        return hitBoxColor = Color.rgb(i, 0, 0);
    }

    /**
     * makes object move in new random direction every 100 frames
     */
    public void move() {
        if (changeDirection > 100) {
            Random r = new Random();
            rotateAngle(getRotate() + r.nextInt(50) % 360);
            changeDirection = 0;
        } else changeDirection++;

    }

    public Color getHitBoxColor() {
        return hitBoxColor;
    }

    public void setHitBoxColor(Color hitBoxColor) {
        this.hitBoxColor = hitBoxColor;
    }

    public double getVelMultiply() {
        return velMultiply;
    }

    public void setVelMultiply(double velMultiply) {
        this.velMultiply = velMultiply;

        setVelocity(getVelocity().multiply(velMultiply));
    }
}

