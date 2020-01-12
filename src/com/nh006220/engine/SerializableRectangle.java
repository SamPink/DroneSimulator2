package com.nh006220.engine;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class SerializableRectangle extends Rectangle implements Serializable {
    public SerializableRectangle(int w, int h) {
        super(w, h);
    }
}
