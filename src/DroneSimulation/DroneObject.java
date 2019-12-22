package DroneSimulation;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.text.MessageFormat;

public class DroneObject {
    private int id;

    private boolean canMove;

    public void setView(Node view) {
        this.view = view;
    }

    private Node view;
    private Point2D velocity;

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    private boolean alive = true;
    private boolean colliding;
    private DroneObject CollidingWithObject = null;

    public DroneObject(Node view) {
        this.view = view;
        colliding = false;
        velocity = new Point2D(1, 0);
        setColliding(false);
    }

    private void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }


    public void update(Arena arena) {

        if (!getColliding()) {
            update();

            if (isColliding(arena)) {
                //System.out.println(d.toString());
                onCollision();
            }
        } else {
            onCollision();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    private void setPosition(int x, int y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    public double getMaxX(){
        double translateX = view.getTranslateX();
        translateX += (int) view.getBoundsInParent().getHeight() / 2;
        return translateX;
    }

    public double getMinX(){
        double translateX = view.getTranslateX();
        translateX -= (int) view.getBoundsInParent().getHeight() / 2;
        return translateX;
    }

    public double getMaxY(){
        double translateY = view.getTranslateY();
        translateY += view.getBoundsInParent().getHeight() / 2;
        return translateY;
    }

    public double getMinY(){
        double translateY = view.getTranslateY();
        translateY -= view.getBoundsInParent().getHeight() / 2;
        return translateY;
    }

    public double getX(){
        return view.getTranslateX();
    }

    public double getY(){
        return view.getTranslateY();
    }

    public void setColliding(boolean b) {
        this.colliding = b;
    }

    public boolean getColliding() {
        return this.colliding;
    }

    private void setCollidingWithObject(DroneObject d) {
        this.CollidingWithObject = d;
    }

    public DroneObject getCollidingWithObject() {
        return getCollidingWithObject();
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateAngle(int angle){
        view.setRotate(angle);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    private boolean isCollidingWithObject(Arena arena) {
        for (DroneObject d : arena.getDrones()) {
            if(!d.equals(this)){
                if (getView().getBoundsInParent().intersects(d.getView().getBoundsInParent())){
                    setCollidingWithObject(d);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCollidingWithArena(Arena arena) {

        return getMaxX() > arena.getSizeX() || getMinX() < 0
                || getMaxY() > arena.getSizeY() || getMinY() < 0;
    }

    public boolean isColliding(Arena arena) {
        if(isCollidingWithArena(arena)){
            setColliding(true);
        }

        if(isCollidingWithObject(arena)){
            setColliding(true);
        }

        return getColliding();
    }

    public void onCollision() {
        rotateAngle((int) getRotate() + 180);

        update();
        update();

        setColliding(false);
    }

    @Override
    public String toString() {
        return MessageFormat.format("DroneObject {0}'{'view={1}, velocity={2}, X=min{3},max{4} Y=min{5},max{6}, colliding={7}, colliding with {8}'}'",
                getId(), getView(), getVelocity(), getMinX(), getMaxX(), getMinY(),getMaxY(), getColliding(), getCollidingWithObject());
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(getX(), getY(), 20, 20);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
    }
}
