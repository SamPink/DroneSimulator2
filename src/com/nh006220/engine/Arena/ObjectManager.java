package com.nh006220.engine.Arena;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.engine.ObjectTemplates.StaticObject;
import com.nh006220.simulator.Objects.MovingObject1;
import com.nh006220.simulator.Objects.MovingObject2;
import com.nh006220.simulator.Objects.StaticObject1;
import com.nh006220.simulator.SETTINGS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectManager implements Serializable {
    private List<MovingObject> movingObjects;
    private List<StaticObject> staticObjects;

    public ObjectManager() {
        this.movingObjects = new ArrayList<>();
        this.staticObjects = new ArrayList<>();
    }

    public List<Object> getAllObjects() {
        List<Object> obj = new ArrayList<>();

        obj.addAll(movingObjects);
        obj.addAll(staticObjects);

        return obj;

    }

    public List<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public List<StaticObject> getStaticObjects() {
        return staticObjects;
    }

    public boolean addMovingObject(MovingObject obj, int posX, int posY) {

        obj.setPos(posX, posY);

        if (canAddObject(obj)) {
            movingObjects.add(obj);
            return true;
        } else {
            System.out.println("oh fuck");
            return false;
        }
    }

    public void addMovingObject(MovingObject obj) {
        Random r = new Random();

        addMovingObject(obj, (r.nextInt(SETTINGS.CanvasWidth) - 50), (r.nextInt(SETTINGS.CanvasHeight) - 50));
    }

    public void addObject(DroneType droneType) {
        if (droneType == DroneType.MovingObject1) {
            addMovingObject(new MovingObject1());
        } else if (droneType == DroneType.MovingObject2) {
            addMovingObject(new MovingObject2());
        } else if (droneType == DroneType.StaticObject1) {
            addStaticObject(new StaticObject1());
        }
    }

    private void addStaticObject(StaticObject staticObject) {
        Random r = new Random();

        addStaticObject(staticObject, (r.nextInt(SETTINGS.CanvasWidth) - 50), (r.nextInt(SETTINGS.CanvasHeight) - 50));
    }


    private boolean arenaFull() {
        //TODO check to see if can add
        return false;
    }

    public void addStaticObject(StaticObject obj, int posX, int posY) {

        obj.setPos(posX, posY);

        staticObjects.add(obj);
    }

    public void moveRandom() {
        for (MovingObject m : movingObjects) {
            Random r = new Random();
            m.rotateAngle(r.nextInt(360));
        }
    }

    private boolean canAddObject(MovingObject obj) {
        return !obj.isColliding(this);
    }

    @Override
    public String toString() {
        return "ObjectManager{" +
                "movingObjects=" + movingObjects +
                ", staticObjects=" + staticObjects +
                '}';
    }

}
