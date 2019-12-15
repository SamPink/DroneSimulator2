package DroneSimulation.Test;

import DroneSimulation.Arena;
import DroneSimulation.Drones.Drone1;
import javafx.geometry.Point2D;

public class DroneTest {

    public static void TestDroneCanMove(){
        Drone1 d1 = new Drone1();

        System.out.printf("%s,%s%n", d1.getX(), d1.getY());

        d1.setVelocity(new Point2D(0,1));
        d1.update();

        System.out.printf("%s,%s%n", d1.getX(), d1.getY());
    }

    public static void TestDroneArenaCollisions(){
        Arena arena = new Arena(400,400);

        arena.addGameObject(new Drone1(), 100,100);
        arena.addGameObject(new Drone1(), 200,100);

        System.out.println(arena.toString());

        arena.rotateDronesRandom();

        arena.update();

        System.out.println(arena.toString());
    }

    public static void TestDroneCantLeaveArena(){
        Arena arena = new Arena(400,400);

        for (int i = 0; i < 20; i++) {
            arena.addGameObjectRandom(new Drone1());
        }

        System.out.println(arena.toString());

        arena.rotateDronesRandom();


        for (int i = 0; i < 10000; i++) {
            //1000 frames
            arena.update();
        }

        System.out.println(arena.toString());
    }

    public static void main(String[] args) {
        //TestDroneCanMove();
        //TestDroneArena();
        TestDroneCantLeaveArena();
    }
}
