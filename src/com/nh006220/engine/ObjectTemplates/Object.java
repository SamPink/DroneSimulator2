package com.nh006220.engine.ObjectTemplates;

import com.nh006220.engine.SerializableImage;
import com.nh006220.engine.SerializablePoint2D;
import com.nh006220.engine.SerializableRectangle;
import com.nh006220.simulator.Simulation2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.text.DecimalFormat;

public abstract class Object implements Serializable {
    private int id;
    private String name;

    private SerializableRectangle rectangle;
    private SerializablePoint2D velocity;
    private SerializableImage image;

    public Object(int w, int h, int xVel, int yVel, String image) {
        this.rectangle = new SerializableRectangle(w, h);
        this.velocity = new SerializablePoint2D(xVel, yVel);
        this.image = new SerializableImage(Simulation2.class.getResourceAsStream(image));
    }

    public Object() {
        int w = 50;
        int h = 50;
        int xVel = 2;
        int yVel = 0;
        String image = "images/drone1.png";
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

    public void setVelocity(SerializablePoint2D velocity) {
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

    public SerializableImage getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = new SerializableImage(Simulation2.class.getResourceAsStream(image));
    }

    /**
     * @param angle to rotate
     */
    public void rotateAngle(int angle) {
        rectangle.setRotate(angle);
        setVelocity(new SerializablePoint2D(
                Math.cos(Math.toRadians(rectangle.getRotate())),
                Math.sin(Math.toRadians(rectangle.getRotate()))
        ));
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

        return getName() + " { " +
                "x pos=" + getX() +
                ", y pos=" + getY() +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", speed X=" + df2.format(getVelocity().getX()) +
                ", speed Y=" + df2.format(getVelocity().getY()) +
                '}';
    }
}
