/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeMain extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {

        // Root pane 
        BorderPane rootPane = new BorderPane();

        // Gridpane for operations 
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(3);
        gridPane.setPadding(new Insets(3, 3, 3, 3));
        rootPane.setTop(gridPane);

        BorderPane canvasPane = new BorderPane();
        canvasPane.setStyle("-fx-background-color: LightGray;");
        rootPane.setCenter(canvasPane);

        // Set canvas on center 
        canvas = new Canvas();
        canvasPane.setCenter(canvas);

        gc = canvas.getGraphicsContext2D();

        // Create operations row 
        createOperationsRow(gridPane, "Tree", 3, 20, 10);

        Scene scene = new Scene(rootPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TREE");
        primaryStage.show();

        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());
    }

    // Stop loop 
    @Override
    public void stop() throws Exception {
        super.stop();
        Tree.stopLoop();
    }

    // Create operation on the top 
    private void createOperationsRow(GridPane gridPane, String name, int row,
            int maxD, int currentDepth) {
        Label lName = new Label(name);
        gridPane.add(lName, 0, row);
        Button btStart = new Button("Start");
        btStart.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(btStart, 1, row);
        Button btClear = new Button("Clear");
        btClear.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(btClear, 2, row);
        Label lDepth = new Label("Depth");
        gridPane.add(lDepth, 3, row);
        Spinner<Integer> spDepth = new Spinner<>(0, maxD, currentDepth);
        gridPane.add(spDepth, 4, row);
        Label lValue = new Label("");
        gridPane.add(lValue, 5, row);

        // Clear button action 
        btClear.setOnAction(e -> {
            try {
                stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        // Start button action 
        btStart.setOnAction(e -> {
            lValue.setText(String.format("Depth: %s,  Width: %s, Height: %s",
                    spDepth.getValue(), canvas.getWidth(), canvas.getHeight()));
            start(spDepth.getValue(), 5.0);
        });

    }

    // Start tree creation 
    private void start(int depth, double scale) {
        tree(depth, scale, canvas.getWidth(), canvas.getHeight());
    }

    // Tree creation start function 
    private void tree(int depth, double scale, double width, double height) {
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(2);
        Tree.startAnimation(gc, depth, width / 2, height, height / scale);
        Tree.startLoop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
