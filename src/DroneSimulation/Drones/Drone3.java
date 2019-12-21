package DroneSimulation.Drones;

import DroneSimulation.DroneObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Drone3 extends DroneObject {
    public Drone3() {
        super(new Circle(10,10,30, Color.BLUE));

        setVelocity(new Point2D(0, 2));
    }
}
