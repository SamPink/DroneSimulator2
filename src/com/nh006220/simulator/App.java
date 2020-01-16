package com.nh006220.simulator;

import com.nh006220.engine.GameWorld;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameWorld simulationApp = new Simulation2("Sam Pink", 60);

        simulationApp.initialize(stage);

        stage.show();
    }
}
