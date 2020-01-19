package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.StaticObject;

public class Building extends StaticObject {
    public Building(int width, int height, String image, DroneType droneType) {
        super(width, height, image, droneType);
    }

    public Building() {
        super(200, 175, "images/building.png", DroneType.building);
        //setName("Static Object 1");
    }
}
