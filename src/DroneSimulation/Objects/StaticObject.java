package DroneSimulation.Objects;

import DroneSimulation.Arena;
import DroneSimulation.DroneObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StaticObject extends DroneObject {
    public StaticObject() {
        super(new Rectangle(500, 20, Color.GREEN));
        setCanMove(false);
    }

    @Override
    public void update(Arena arena) {
    }

    @Override
    public void rotateAngle(int angle) {
    }
}
