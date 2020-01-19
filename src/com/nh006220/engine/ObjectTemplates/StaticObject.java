package com.nh006220.engine.ObjectTemplates;

/**
 * implements object
 * all static objects inherit
 * object that cant move
 */
public abstract class StaticObject extends Object {
    public StaticObject(int width, int height, String image, DroneType droneType) {
        super(width, height, 0, 0, image, droneType);
    }

    /**
     * sets the position of the shape to be its current position + velocity
     * called every frame
     */
    @Override
    public void update() {
        //don't move on update because its static
    }
}
