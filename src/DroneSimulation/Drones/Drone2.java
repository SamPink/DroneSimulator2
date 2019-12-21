package DroneSimulation.Drones;

import DroneSimulation.DroneObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Drone2 extends DroneObject {
    public Drone2() {
        super(new Rectangle(15,15, Color.GREEN));
    }

    @Override
    public void onCollision() {
        rotateAngle((int) getRotate() + 180);
        setColliding(false);
    }
}
