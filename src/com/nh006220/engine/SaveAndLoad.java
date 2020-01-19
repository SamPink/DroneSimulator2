package com.nh006220.engine;

import com.nh006220.engine.Arena.DroneArena;

import java.io.*;

/**
 * file io handler for save and load
 * uses drone arena DroneArenaSave to convert to a from a serializable format
 */
class SaveAndLoad {
    /**
     * Stores arena to be saved and loaded
     */
    private DroneArena arena;

    /**
     * default file save name
     */
    private String fileName = "defaultSave.txt";

    /**
     * no constructor parameters
     */
    SaveAndLoad() {
    }

    /**
     * saves passed DroneArena to fileName
     *
     * @param arena to save
     */
    void saveToFile(DroneArena arena) {
        this.arena = arena;

        try {
            FileOutputStream f = new FileOutputStream(new File(fileName));
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

    /**
     * loads arena into memory from specified file location
     *
     * @param s file to load
     * @return serialized format of drone arena
     */
    DroneArenaSave loadFromFile(String s) {
        FileInputStream fi;
        DroneArenaSave pr1 = new DroneArenaSave();
        try {
            fi = new FileInputStream(new File(s));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            pr1 = (DroneArenaSave) oi.readObject();

            oi.close();
            fi.close();


        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return pr1;
    }


    /**
     * appends .txt to file name if not typed
     *
     * @param text returns file name to be saved
     */
    void setFileName(String text) {
        if (!text.contains(".txt")) {
            fileName = text + ".txt";
        }
    }
}
