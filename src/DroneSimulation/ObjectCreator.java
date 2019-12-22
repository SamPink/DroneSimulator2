package DroneSimulation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObjectCreator {
    private Pane view;
    private Button button;
    private Rectangle rectangle;
    private GraphicsContext gc;
    private Canvas canvas;

    public ObjectCreator() {
        this.view = new Pane();
        this.canvas = new Canvas(500, 500);
        this.gc = canvas.getGraphicsContext2D();
        this.button = new Button("add");
        this.rectangle = new Rectangle(400, 400, Color.BLUE);

        setView();
    }

    private void setView() {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        this.view.getChildren().add(button);
        //this.view.getChildren().add(rectangle);
        this.view.getChildren().add(canvas);

        this.canvas.setOnMousePressed(mouseEvent -> {
                    gc.beginPath();
                    gc.moveTo(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                    gc.stroke();
                }
        );
        this.canvas.setOnMouseDragged(mouseEvent -> {
                    gc.lineTo((int) mouseEvent.getX(), (int) mouseEvent.getY());
                    gc.stroke();
                }
        );
        this.button.setOnAction(actionEvent -> {
            System.out.println("hey" + gc.toString());
        });
    }

    public Pane getView() {
        return view;
    }
}
