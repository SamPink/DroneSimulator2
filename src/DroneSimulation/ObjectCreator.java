package DroneSimulation;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ObjectCreator {
    private Pane view;
    private Button button;

    public ObjectCreator() {
        this.view = new Pane();
        this.button = new Button("add");

        setView();
    }

    private void setView() {
        this.button.setOnAction(actionEvent -> System.out.println("hey"));
        this.view.getChildren().add(button);
    }

    public Pane getView() {
        return view;
    }
}
