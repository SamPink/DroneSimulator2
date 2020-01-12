package com.nh006220.simulator;

import com.nh006220.engine.Arena.DroneArena;

import java.io.*;

public class SaveAndLoad {

    private DroneArena arena;

    public SaveAndLoad() {
    }

    public void saveToFile(DroneArena arena) {
        this.arena = arena;
        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            DroneArenaSave ds = new DroneArenaSave();
            ds.arenaSave(arena);

            // Write objects to file
            o.writeObject(ds);

            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DroneArenaSave loadFromFile() {
        FileInputStream fi = null;
        DroneArenaSave pr1 = new DroneArenaSave();
        try {
            fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            pr1 = (DroneArenaSave) oi.readObject();
            System.out.println(pr1.toString());

            oi.close();
            fi.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pr1;
    }
}
