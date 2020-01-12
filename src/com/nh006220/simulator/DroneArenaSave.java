package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.simulator.Objects.MovingObject1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DroneArenaSave implements Serializable {
    private int width, height;
    private List<DroneStore> droneStores;

    public DroneArenaSave() {
    }

    public void arenaSave(DroneArena arena) {
        droneStores = new ArrayList<>();
        for (Object ob : arena.getObjectManager().getAllObjects()) {
            droneStores.add(new DroneStore(
                    ob.getWidth(),
                    ob.getHeight(),
                    ob.getVelocity().getX(),
                    ob.getVelocity().getY(),
                    ob.getImage().getUrl()
            ));
        }

        width = arena.getWidth();
        height = arena.getHeight();
    }

    public DroneArena arenaLoad() {
        DroneArena arena = new DroneArena();

        for (DroneStore object : droneStores) {
            arena.getObjectManager().addMovingObject(
                    new MovingObject1()
            );
        }

        return arena;
    }

    @Override
    public String toString() {
        return "DroneArenaSave{" +
                "width=" + width +
                ", height=" + height +
                ", droneStores=" + droneStores +
                '}';
    }

    private class DroneStore implements Serializable {
        private int width, height;
        private double xVel, yVel;
        private String image;

        public DroneStore(int width, int height, double xVel, double yVel, String image) {
            this.width = width;
            this.height = height;
            this.xVel = xVel;
            this.yVel = yVel;
            this.image = image;
        }

        @Override
        public String toString() {
            return "DroneStore{" +
                    "width=" + width +
                    ", height=" + height +
                    ", xVel=" + xVel +
                    ", yVel=" + yVel +
                    ", image='" + image + '\'' +
                    '}';
        }
    }
}
