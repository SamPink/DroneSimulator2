package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.MovingObject;

import java.io.Serializable;

public class MovingObject1 extends MovingObject implements Serializable {
    public MovingObject1() {
        super(50, 50, 0, 1, "images/drone1.png");
        //Random r1 = new Random();
        //rotateAngle(r1.nextInt(360));
        //setImage("images/drone1.png");
        //setName("Moving object 1");
    }
}
