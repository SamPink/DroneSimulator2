package com.nh006220.engine.ObjectTemplates;

import com.nh006220.simulator.Simulation2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.text.DecimalFormat;

public abstract class Object {
    private final String imageString;
    private DroneType droneType;
    private int id;
    private String name;
    private Rectangle rectangle;
    private Point2D velocity;
    private Image image;
    private int velMultiply = 1;

    public Object(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        this.droneType = droneType;

        this.rectangle = new Rectangle(w, h);
        this.velocity = new Point2D(xVel, yVel);
        this.imageString = image;
        setImage(imageString);

    }

    public int getVelMultiply() {
        return velMultiply;
    }

    public void setVelMultiply(int velMultiply) {
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

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getRotate() {
        return (int) rectangle.getRotate();
    }

    public void setPos(int posX, int posY) {
        rectangle.setTranslateX(posX);
        rectangle.setTranslateY(posY);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = new Image(Simulation2.class.getResourceAsStream(image));
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

        return getDroneType().toString() + " { " +
                "x pos=" + getX() +
                ", y pos=" + getY() +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", speed X=" + df2.format(getVelocity().getX()) +
                ", speed Y=" + df2.format(getVelocity().getY()) +
                '}';
    }
}
