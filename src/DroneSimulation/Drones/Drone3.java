package DroneSimulation.Drones;

import DroneSimulation.DroneObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;

public class Drone3 extends DroneObject {
    public Drone3() {
        super(new Circle(10,10,15, Color.BLUE));
    }
}
