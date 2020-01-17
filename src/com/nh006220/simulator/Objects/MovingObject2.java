package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;

public class MovingObject2 extends MovingObject {
    public MovingObject2(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        super(w, h, xVel, yVel, image, droneType);
    }

    public MovingObject2() {
        super(30, 30, 2, 0, "images/drone2.png", DroneType.MovingObject2);
        setHitBoxRange(1.5);
    }

    @Override
    public void move() {
        super.move();
    }
}
