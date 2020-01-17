package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;

public class FastDrone extends MovingObject {
    public FastDrone(int width, int height, double xVel, double yVel, String image, DroneType droneType) {
        super(width, height, xVel, yVel, image, droneType);
    }

    public FastDrone() {
        super(50, 50, 1, 0, "images/drone3.png", DroneType.FastDrone);
        setDrawHitBox(false);
        setHealth(1000);
        setVelMultiply(5);
    }
}
