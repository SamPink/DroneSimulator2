package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;

import java.util.Random;

public class MovingObject1 extends MovingObject {
    public MovingObject1(int width, int height, double xVel, double yVel, String image, DroneType droneType) {
        super(width, height, xVel, yVel, image, droneType);
        Random r1 = new Random();
        rotateAngle(r1.nextInt(360));
        //setName("Moving object 1");
    }

    public MovingObject1() {
        super(50, 50, 1, 0, "images/drone1.png", DroneType.MovingObject1);
    }
}
