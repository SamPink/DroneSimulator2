package com.nh006220.engine.Objects;

import com.nh006220.simulator.SimulationApp;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Object {
    private int id;

    private Rectangle rectangle;
    private Point2D velocity;
    private Image image;

    public Object(int width, int height) {
        this.rectangle = new Rectangle(width, height);
        this.velocity = new Point2D(0, 0);
        setImage("images/drone1.png");
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
        this.image = new Image(SimulationApp.class.getResourceAsStream(image));
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
        return "Object{" +
                "id=" + id +
                ", rectangle=" + rectangle +
                ", velocity=" + velocity +
                '}';
    }
}
