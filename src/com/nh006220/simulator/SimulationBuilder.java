package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.DroneType;

public class SimulationBuilder {

    public static DroneArena basicArena() {
        DroneArena arena = new DroneArena();

        arena.getObjectManager().addObject(DroneType.MovingObject1);

        return arena;
    }
}
