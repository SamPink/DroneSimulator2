package DroneSimulation;

import DroneSimulation.Drones.Drone1;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    int sizeX, sizeY;
    List<DroneObject> drones;

    public Arena(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.drones = new ArrayList<>();
    }


    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public List<DroneObject> getDrones() {
        return drones;
    }

    public void setDrones(List<DroneObject> drones) {
        this.drones = drones;
    }

    private boolean canAddHere(DroneObject drone1) {

        if(drone1.isColliding(this)){
            return false;
        }

        return true;
    }
    public void addGameObject(DroneObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);

        if(object.isColliding(this)){
            System.out.println("Cant add");
        }else{
            object.setId(drones.size()+1);

            drones.add(object);
        }
    }

    public void addGameObjectRandom(DroneObject drone1) {
        Random r = new Random();

        addGameObject(drone1, r.nextInt(getSizeX()), r.nextInt(getSizeY()));
    }

    public void rotateDronesRandom(){
        for (DroneObject d: drones){
            Random r = new Random();
            d.rotateAngle(r.nextInt(360));
        }
    }

    public void updateGame(Pane root){
        root.getChildren().clear();
        for (DroneObject d: drones){
            if(!d.getColliding()){
                d.update();

                if(d.isColliding(this)){
                    //System.out.println(d.toString());
                    d.onCollision();
                }
            }else{
                d.onCollision();
            }

            root.getChildren().add(d.getView());
        }
    }
    public void updateRight(Pane right) {
        right.getChildren().clear();

        HBox hBox = new HBox();
        for (DroneObject d: drones) {
            hBox.getChildren().add(new TextField(d.toString()));
        }

        right.getChildren().add(hBox);

    }


    public String logDrones(){
        String s = "Drones: \n";

        for (DroneObject d: drones){
            s += "\t"+d.toString()+"\n";
        }

        return s;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", " +logDrones() +
                '}';
    }
}
