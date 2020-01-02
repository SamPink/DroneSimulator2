package com.nh006220.simulator;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SimulationApp simulationApp = new SimulationApp();

        stage.setTitle("Nh006220");

        stage.setScene(simulationApp.createScene());
        stage.setResizable(false);

        stage.show();
    }
}
