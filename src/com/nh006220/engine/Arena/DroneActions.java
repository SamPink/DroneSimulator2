package com.nh006220.engine.Arena;

import com.nh006220.engine.ObjectTemplates.MovingObject;

import java.util.Random;

public class DroneActions {


    public void rotateRandom(ObjectManager objectManager) {
        Random r = new Random();
        for (MovingObject m : objectManager.getMovingObjects()) {
            m.rotateAngle(r.nextInt(360));
        }
    }
}
