package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;
import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.simulator.Objects.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to convert the drone arena into a serializable format
 * and load it back into a droneArena object from a text file
 */
public class DroneArenaSave implements Serializable {
    /**
     * width and height of the arena
     */
    private int width, height;
    /**
     * class path location to get background image from
     */
    private String image;

    /**
     * Serializable list which stores drone object
     */
    private List<DroneStore> droneStores;

    /**
     * no constructor parameters
     */
    public DroneArenaSave() {
    }

    /**
     * converts DroneArena object into serializable object
     * to be saved
     *
     * @param arena DroneArena object to be saved
     */
    void arenaSave(DroneArena arena) {
        width = arena.getWidth();
        height = arena.getHeight();
        image = arena.getBackgroundLocation();
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

    /**
     * loads arena from set text file location
     *
     * @return loaded DroneArena object
     */
    DroneArena arenaLoad() {
        DroneArena arena = new DroneArena();

        arena.setBackground(image);

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
            } else if (object.droneType == DroneType.FastDrone) {
                arena.getObjectManager().addMovingObject(new FastDrone(object.width, object.height, object.xVel, object.yVel, object.image, object.droneType),
                        object.xPos,
                        object.yPos);
            } else if (object.droneType == DroneType.building) {
                arena.getObjectManager().addStaticObject(
                        new Building(object.width, object.height, object.image, object.droneType),
                        object.xPos,
                        object.yPos);
            } else if (object.droneType == DroneType.StaticTree) {
                arena.getObjectManager().addStaticObject(
                        new StaticTree(object.width, object.height, object.image, object.droneType),
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

    /**
     * class used to convert a game object into a serializable format
     */
    private static class DroneStore implements Serializable {
        private final DroneType droneType;
        private final int width;
        private final int height;
        private final double xVel;
        private final double yVel;
        private final int xPos;
        private final int yPos;
        private final String image;

        /**
         * @param droneType enum class storing drone type
         * @param width     of the object
         * @param height    of the object
         * @param xVel      speed in x direction
         * @param yVel      speed in y direction
         * @param xPos      position in arena
         * @param yPos      position in arena
         * @param image     class path image location
         */
        DroneStore(DroneType droneType, int width, int height, double xVel, double yVel, int xPos, int yPos, String image) {
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
