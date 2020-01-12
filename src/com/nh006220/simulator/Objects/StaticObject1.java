package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.StaticObject;

public class StaticObject1 extends StaticObject {

    public StaticObject1(int width, int height, String image, DroneType droneType) {
        super(width, height, image, droneType);
    }

    public StaticObject1() {
        super(100, 100, "images/background.jpg", DroneType.StaticObject1);
        //setName("Static Object 1");
    }
}
