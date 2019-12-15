package DroneSimulation.Drones;

import DroneSimulation.DroneObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Player extends DroneObject {
    Player() {
        super(new Rectangle(40, 20, Color.BLUE));
    }
}