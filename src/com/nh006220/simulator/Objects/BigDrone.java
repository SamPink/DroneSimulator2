package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;

public class BigDrone extends MovingObject {
    public BigDrone(int w, int h, double xVel, double yVel, String image, DroneType droneType) {
        super(w, h, xVel, yVel, image, droneType);
    }

    public BigDrone() {
        super(80, 60, 0.5, 3, "images/drone4.png", DroneType.BigDrone);
        //setHitBoxRange(2);
    }
}
