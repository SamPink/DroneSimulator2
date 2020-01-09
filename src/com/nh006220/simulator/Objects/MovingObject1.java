package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.MovingObject;

import java.util.Random;

public class MovingObject1 extends MovingObject {
    public MovingObject1() {
        super(30, 30);
        Random r1 = new Random();
        rotateAngle(r1.nextInt(360));
        setImage("images/drone1.png");
        setName("Moving object 1");
    }
}
