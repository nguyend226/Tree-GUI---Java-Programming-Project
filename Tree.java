/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Tree extends AnimationTimer {

    private GraphicsContext gc;
    
    // Starting coordinates 
    private double x, y;
    // Starting length 
    private double length;
    // Depth of the tree 
    private int depth;

    // Length factor of the branches 
    private double lengthFactor = 3.0 / 4.0;
    // Angle between two branches 
    private double angleDelta = Math.PI / 6.0;
    // Branch width factor 
    private double widthFactor = 1.0 / 2.5;

    private static Tree t;

    private Tree() {
        super();
    }

    // Start the animation 
    public static void startAnimation(GraphicsContext gc, int depth, double x, double y, double length) {
        if (t == null) {
            t = new Tree();
        } else {
            t.stop();
        }
        t.gc = gc;
        t.x = x;
        t.y = y;
        t.length = length;
        t.depth = depth;
    }
    
    // Start the loop 
    public static void startLoop() {
        if (t != null) {
            t.start();
        }
    }

    // Stop the loop
    public static void stopLoop() {
        if (t != null) {
            t.stop();
        }
    }

    @Override
    public void handle(long now) {
        tree(depth, x, y, length, Math.PI + 300);
    }

    public void tree(int depth, double x, double y, double length, double alpha) {

        // Base case
        if (depth == 0) {
            stop();
            return;
        }

        // Distance to travel
        double dx = length * Math.cos(alpha);
        double dy = length * Math.sin(alpha);

        // End points
        double endX = x + dx;
        double endY = y - dy;

        // Set line width
        gc.setLineWidth(depth * widthFactor);
        // Draw line
        gc.strokeLine(x, y, endX, endY);

        // Right
        tree(depth - 1, endX, endY, length * lengthFactor, alpha + angleDelta); 
        // Left
        tree(depth - 1, endX, endY, length * lengthFactor, alpha - angleDelta); 

    }
}
