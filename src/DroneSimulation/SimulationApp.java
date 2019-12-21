package DroneSimulation;

import DroneSimulation.Drones.Drone1;
import DroneSimulation.Drones.Drone2;
import DroneSimulation.Drones.Drone3;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class SimulationApp {
    private Arena arena;
    private AnimationTimer timer;
    private BorderPane bp;
    private Pane center,right,bottom;
    private VBox vBox;

    public Scene createScene(){

        bp = new BorderPane();

        center = new Pane();
        center.setPadding(new Insets(20, 20, 20, 20));

        vBox = new VBox();

        bp.setCenter(center);

        bp.setRight(right);

        bp.setBottom(setBottom());

        center.setStyle("-fx-background-color: black;");

        return new Scene(bp, 1200, 1000);
    }

    private Node menuScreen(){
        Node menu = new Pane();


        return menu;
    }

    private Node setBottom() {
        bottom = new HBox();

        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button spawnDrone = new Button("Spawn Drone");
        Button spawnDrone2 = new Button("Spawn Drone 2");
        Button spawnDrone3 = new Button("Spawn Drone 3");
        Button moveRandom = new Button("Move random");

        stop.setOnAction((ActionEvent) -> {
            pause();
        });

        start.setOnAction((ActionEvent) -> {
            init();
        });

        spawnDrone.setOnAction((ActionEvent) -> {
            arena.addGameObjectRandom(new Drone1());
        });

        spawnDrone2.setOnAction((ActionEvent) -> {
            arena.addGameObjectRandom(new Drone2());
        });

        spawnDrone3.setOnAction((ActionEvent) -> {
            arena.addGameObjectRandom(new Drone3());
        });

        moveRandom.setOnAction((ActionEvent) -> {
            arena.rotateDronesRandom();
        });

        bottom.getChildren().add(start);
        bottom.getChildren().add(stop);
        bottom.getChildren().add(spawnDrone);
        bottom.getChildren().add(spawnDrone2);
        bottom.getChildren().add(spawnDrone3);
        bottom.getChildren().add(moveRandom);

        return bottom;
    }

    public void init(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate(now);
            }
        };

        arena = new Arena((int)center.getWidth(),(int)center.getHeight());

        arena.addGameObjectRandom(new Drone1());

        timer.start();
    }


    public void pause(){
        timer.stop();
    }

    public void setPlayerDrone(KeyEvent e , DroneObject d){
        if (e.getCode() == KeyCode.UP) {
            d.rotateAngle(-90);
        } else if (e.getCode() == KeyCode.DOWN) {
            d.rotateAngle(90);
        } else if (e.getCode() == KeyCode.LEFT) {
            d.rotateAngle(180);
        }else if (e.getCode() == KeyCode.RIGHT) {
            d.rotateAngle(-180);
        }
    }

    private void onUpdate(long now) {
        arena.updateGame(center);

        //arena.updateRight(right);
    }
}

