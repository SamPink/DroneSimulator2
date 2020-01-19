package com.nh006220.simulator.Objects;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.StaticObject;

public class StaticTree extends StaticObject {
    public StaticTree(int width, int height, String image, DroneType droneType) {
        super(width, height, image, droneType);
    }

    public StaticTree() {
        super(200, 200, "images/tree.png", DroneType.StaticTree);
    }
}
