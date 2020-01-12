package com.nh006220.engine;

import javafx.geometry.Point2D;

import java.io.Serializable;

public class SerializablePoint2D extends Point2D implements Serializable {
    public SerializablePoint2D(double x, double y) {
        super(x, y);
    }
}
