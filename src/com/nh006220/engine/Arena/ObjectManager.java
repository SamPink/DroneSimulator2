package com.nh006220.engine.Arena;

import com.nh006220.engine.Objects.MovingObject;
import com.nh006220.engine.Objects.StaticObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectManager {
    private List<MovingObject> movingObjects;
    private List<StaticObject> staticObjects;

    public ObjectManager() {
        this.movingObjects = new ArrayList<>();
        this.staticObjects = new ArrayList<>();
    }

    public List<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public List<StaticObject> getStaticObjects() {
        return staticObjects;
    }

    public void addMovingObject(MovingObject obj, int posX, int posY) {
        //TODO canAddObject()

        obj.setPos(posX, posY);

        movingObjects.add(obj);
    }

    public void addStaticObject(StaticObject obj, int posX, int posY) {
        //TODO canAddObject()

        obj.setPos(posX, posY);

        staticObjects.add(obj);
    }

    public void moveRandom() {
        for (MovingObject m : movingObjects) {
            Random r = new Random();
            m.rotateAngle(r.nextInt(360));
        }
    }

    @Override
    public String toString() {
        return "ObjectManager{" +
                "movingObjects=" + movingObjects +
                ", staticObjects=" + staticObjects +
                '}';
    }
}
