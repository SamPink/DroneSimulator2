package com.nh006220.engine.ObjectTemplates;

import com.nh006220.simulator.Simulation;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.DecimalFormat;

/**
 * parent game object from which all other objects inherit
 * objects location is based of a JavaFx shape object
 * velocity of object is based of a JAvaFx point2D
 * the view of the object is an image drawn at location of shape
 */
public abstract class Object {
    /**
     * class path to location of image to be shown
     */
    private final String imageString;
    /**
     * enum type
     */
    private DroneType droneType;
    /**
     * name of the drone object
     */
    private String name;
    /**
     * shape used to keep the location of the object in the arena
     */
    private Rectangle rectangle;
    /**
     * stores the speed and direction of the object
     */
    private Point2D velocity;
    /**
     * image for the view of the object
     */
    private Image image;
    /**
     * used to increase the speed of a object by scale
     */
    private double velMultiply = 1;
    /**
     * used to determine if an object is moving or not
     */
    private boolean moving = false;

    /**
     * constructor for creating a new object
     *
     * @param w         width of object
     * @param h         height of object
     * @param xVel      x speed
     * @param yVel      y speed
     * @param image     class path for image
     * @param droneType enum type
     */
    public Object(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        this.droneType = droneType;
        this.rectangle = new Rectangle(w, h);
        this.velocity = new Point2D(xVel, yVel);
        this.imageString = image;
        setImage(imageString);
    }

    public double getVelMultiply() {
        return velMultiply;
    }

    public void setVelMultiply(double velMultiply) {
        this.velMultiply = velMultiply;

        setVelocity(getVelocity().multiply(velMultiply));
    }

    public DroneType getDroneType() {
        return droneType;
    }

    public void setDroneType(DroneType droneType) {
        this.droneType = droneType;
    }

    public String getImageString() {
        return imageString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return (int) rectangle.getTranslateX();
    }

    public int getY() {
        return (int) rectangle.getTranslateY();
    }

    public int getWidth() {
        return (int) rectangle.getWidth();
    }

    public int getHeight() {
        return (int) rectangle.getHeight();
    }

    public void setWidth(int value) {
        rectangle.setWidth(value);
    }

    public void setHeight(int value) {
        rectangle.setHeight(value);
    }

    public Point2D getVelocity() {
        return velocity;
    }

    void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    Rectangle getRectangle() {
        return rectangle;
    }


    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRotate() {
        return (int) rectangle.getRotate();
    }

    public void setPos(int posX, int posY) {
        rectangle.setTranslateX(posX);
        rectangle.setTranslateY(posY);
    }

    public void setX(int x) {
        rectangle.setTranslateX(x);
    }

    public void setY(int y) {
        rectangle.setTranslateY(y);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = new Image(Simulation.class.getResourceAsStream(image));
    }

    /**
     * @param angle to rotate
     */
    public void rotateAngle(int angle) {
        rectangle.setRotate(angle);
        setVelocity(new Point2D(
                Math.cos(Math.toRadians(rectangle.getRotate())),
                Math.sin(Math.toRadians(rectangle.getRotate()))
        ));
        setVelocity(getVelocity().multiply(velMultiply));
    }

    /**
     * sets the position of the shape to be its current position + velocity
     * called every frame
     */
    public void update() {
        //TODO if moving or have moved
        rectangle.setTranslateX(rectangle.getTranslateX() + velocity.getX());
        rectangle.setTranslateY(rectangle.getTranslateY() + velocity.getY());
    }

    /**
     * draws image in the location of the rectangle
     * if image cant be drawn draw it as a shape
     * @param gc graphics context of the canvas to draw to
     */
    public void draw(GraphicsContext gc) {
        //TODO draw an image. or maybe an animation
        try {
            gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
        } catch (Exception e) {
            gc.setFill(Color.GREEN);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public String toString() {
        DecimalFormat df2 = new DecimalFormat("#.##");

        return getDroneType().toString() + " \n\t{ " +
                "pos x,y = " + getX() + "," + getY() +
                " , speed x,y= " + df2.format(getVelocity().getX()) +
                " ," + df2.format(getVelocity().getY()) +
                " , width=" + getWidth() +
                ", height=" + getHeight() +
                " }";
    }

    public boolean isMoving() {
        return moving;
    }

    void setMoving(boolean moving) {
        this.moving = moving;
    }

}
