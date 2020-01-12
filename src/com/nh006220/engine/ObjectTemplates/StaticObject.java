package com.nh006220.engine.ObjectTemplates;

import java.io.Serializable;

public abstract class StaticObject extends Object implements Serializable {
    public StaticObject() {
        super();
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
