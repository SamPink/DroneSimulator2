package DroneSimulation.Drones;

import DroneSimulation.DroneObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Drone1 extends DroneObject {
    public Drone1() {
        super(new Circle(5, 5, 15, Color.BROWN));
    }

    @Override
    public void onCollision() {


        rotateAngle((int) getRotate() + 180);
        setVelocity(getVelocity().multiply(2));
        setColliding(false);
    }
}
