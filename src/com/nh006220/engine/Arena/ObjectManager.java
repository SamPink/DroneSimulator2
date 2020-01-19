package com.nh006220.engine.Arena;

import com.nh006220.engine.ObjectTemplates.DroneType;
import com.nh006220.engine.ObjectTemplates.MovingObject;
import com.nh006220.engine.ObjectTemplates.Object;
import com.nh006220.engine.ObjectTemplates.StaticObject;
import com.nh006220.engine.SETTINGS;
import com.nh006220.simulator.Objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * managers the adding and removing of game objects
 * stores an arrayList of both moving and static objects
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class ObjectManager {
    /**
     * stores moving objects in the game
     */
    private final List<MovingObject> movingObjects;
    /**
     * stores static objects in the game
     */
    private final List<StaticObject> staticObjects;

    /**
     * default constructor
     */
    ObjectManager() {
        this.movingObjects = new ArrayList<>();
        this.staticObjects = new ArrayList<>();
    }

    /**
     * @return moving and static objects
     */
    public List<Object> getAllObjects() {
        List<Object> obj = new ArrayList<>();

        obj.addAll(movingObjects);
        obj.addAll(staticObjects);

        return obj;

    }

    List<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    List<StaticObject> getStaticObjects() {
        return staticObjects;
    }

    /**
     * adding a new moving object
     *
     * @param obj  object to add
     * @param posX position in arena
     * @param posY position in arena
     * @return true of added else false
     */
    public boolean addMovingObject(MovingObject obj, int posX, int posY) {
        obj.setPos(posX, posY);
        if (canAddObject(obj)) {
            movingObjects.add(obj);
            return true;
        }

        return false;
    }

    /**
     * add moving object into random location
     * calls addMovingObject(Obj, x, y)
     *
     * @param obj object to add
     */
    public void addMovingObject(MovingObject obj) {
        int x = 0;
        Random r = new Random();

        while (x <= 1000) {
            if (!addMovingObject(obj, (r.nextInt(SETTINGS.CanvasWidth) - 50), (r.nextInt(SETTINGS.CanvasHeight) - 50))) {
                x++;
            } else {
                break;
            }
        }
    }

    /**
     * adds object based off DroneType enum
     * calls add object to random location
     *
     * @param droneType object type to add
     */
    public void addObject(DroneType droneType) {
        if (droneType == DroneType.MovingObject1) addMovingObject(new MovingObject1());
        else if (droneType == DroneType.MovingObject2) addMovingObject(new MovingObject2());
        else if (droneType == DroneType.StaticObject1) addStaticObject(new StaticObject1());
        else if (droneType == DroneType.BigDrone) addMovingObject(new BigDrone());
        else if (droneType == DroneType.FastDrone) addMovingObject(new FastDrone());
        else if (droneType == DroneType.building) addStaticObject(new Building());
        else if (droneType == DroneType.StaticTree) addStaticObject(new StaticTree());
    }

    /**
     * adds static object to random location
     *
     * @param staticObject object to add
     */
    private void addStaticObject(StaticObject staticObject) {
        Random r = new Random();

        addStaticObject(staticObject, (r.nextInt(SETTINGS.CanvasWidth) - 50), (r.nextInt(SETTINGS.CanvasHeight) - 50));
    }


    /**
     * adds static object to game in location
     *
     * @param obj  object to  add
     * @param posX position in arena
     * @param posY position in arena
     */
    public void addStaticObject(StaticObject obj, int posX, int posY) {

        obj.setPos(posX, posY);

        staticObjects.add(obj);
    }

    /**
     * rotate all game objects random
     */
    public void moveRandom() {
        for (MovingObject m : movingObjects) {
            Random r = new Random();
            m.rotateAngle(r.nextInt(360));
        }
    }

    /**
     * test if object can be added in this location
     * @param obj object to try and add
     * @return true of not colliding in this location
     */
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

    /**
     * remove moving object from game manager
     * @param m object to remove
     */
    public void removeMoving(MovingObject m) {
        System.out.println("removed " + m);
        movingObjects.remove(m);
    }

    /**
     * remove static object from game manager
     *
     * @param i object to remove
     */
    public void removeStatic(Object i) {
        staticObjects.remove(i);
    }
}
