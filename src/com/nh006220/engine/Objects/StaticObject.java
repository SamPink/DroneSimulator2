package com.nh006220.engine.Objects;

public abstract class StaticObject extends Object {
    public StaticObject(int width, int height) {
        super(width, height);
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
