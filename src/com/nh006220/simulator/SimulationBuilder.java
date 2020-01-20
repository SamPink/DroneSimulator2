package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.SETTINGS;
import com.nh006220.simulator.Objects.FastDrone;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.Objects.StaticTree;
import javafx.scene.canvas.Canvas;

/**
 * static class used to load pre set arena into the simulation
 */
class SimulationBuilder {

    static DroneArena basicArena() {
        DroneArena arena = new DroneArena();

        arena.getObjectManager().addObject(DroneType.MovingObject1);

        return arena;
    }

    public static DroneArena buildingsArena() {
        return new DroneArena();
    }

    static DroneArena woodsArena() {
        DroneArena arena = new DroneArena();

        arena.getObjectManager().addStaticObject(new StaticTree(), 100, 380);
        arena.getObjectManager().addStaticObject(new StaticTree(), 280, 380);
        arena.getObjectManager().addStaticObject(new StaticTree(), 500, 380);
        arena.getObjectManager().addStaticObject(new StaticTree(), 700, 380);


        arena.getObjectManager().addMovingObject(new MovingObject1());

        arena.setBackground("images/mountain.png");

        SETTINGS.GroundOnBackgroundImage = 500;

        return arena;

    }

    public static DroneArena playerMode(Canvas canvas) {
        DroneArena arena = new DroneArena();

        arena.getObjectManager().addMovingObject(new FastDrone());

        canvas.setOnKeyPressed(keyEvent -> {
            System.out.println(keyEvent.getCode());
        });

        return arena;
    }
}
