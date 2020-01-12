package com.nh006220.engine;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.io.Serializable;

public class SerializableImage extends Image implements Serializable {
    public SerializableImage(InputStream url) {
        super(url);
    }
}
