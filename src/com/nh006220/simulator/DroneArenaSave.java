package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.simulator.Objects.BigDrone;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.Objects.MovingObject2;
import com.nh006220.simulator.Objects.StaticObject1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DroneArenaSave implements Serializable {
    private int width, height;
    private List<DroneStore> droneStores;

    public DroneArenaSave() {
    }

    public void arenaSave(DroneArena arena) {
        width = arena.getWidth();
        height = arena.getHeight();
        droneStores = new ArrayList<>();
        for (Object ob : arena.getObjectManager().getAllObjects()) {
            //TODO needs to save type
            droneStores.add(new DroneStore(
                    ob.getDroneType(),
                    ob.getWidth(),
                    ob.getHeight(),
                    ob.getVelocity().getX(),
                    ob.getVelocity().getY(),
                    ob.getX(),
                    ob.getY(),
                    ob.getImageString()
            ));
        }
    }

    public DroneArena arenaLoad() {
        DroneArena arena = new DroneArena();

        System.out.println(droneStores.toString());

        for (DroneStore object : droneStores) {
            if (object.droneType == DroneType.MovingObject1) {
                arena.getObjectManager().addMovingObject(
                        new MovingObject1(object.width, object.height, object.xVel, object.yVel, object.image, object.droneType),
                        object.xPos,
                        object.yPos
                );
            } else if (object.droneType == DroneType.MovingObject2) {
                arena.getObjectManager().addMovingObject(
                        new MovingObject2(object.width, object.height, object.xVel, object.yVel, object.image, object.droneType),
                        object.xPos,
                        object.yPos
                );
            } else if (object.droneType == DroneType.StaticObject1) {
                arena.getObjectManager().addStaticObject(
                        new StaticObject1(object.width, object.height, object.image, object.droneType),
                        object.xPos,
                        object.yPos);
            } else if (object.droneType == DroneType.BigDrone) {
                arena.getObjectManager().addMovingObject(new BigDrone(object.width, object.height, object.xVel, object.yVel, object.image, object.droneType),
                        object.xPos,
                        object.yPos);
            }
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
        private DroneType droneType;
        private int width, height;
        private double xVel, yVel;
        private int xPos, yPos;
        private String image;

        public DroneStore(DroneType droneType, int width, int height, double xVel, double yVel, int xPos, int yPos, String image) {
            this.droneType = droneType;
            this.width = width;
            this.height = height;
            this.xVel = xVel;
            this.yVel = yVel;
            this.image = image;
            this.xPos = xPos;
            this.yPos = yPos;
        }

        @Override
        public String toString() {
            return "DroneStore{" +
                    "droneType=" + droneType +
                    ", width=" + width +
                    ", height=" + height +
                    ", xVel=" + xVel +
                    ", yVel=" + yVel +
                    ", xPos=" + xPos +
                    ", yPos=" + yPos +
                    ", image='" + image + '\'' +
                    '}';
        }
    }
}
