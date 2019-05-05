package pathfindinggame;

import java.awt.*;
import java.awt.event.*;

public class PathFrame extends Frame {
    public PathCanvas canvas;
    public PathFrame() {
        this.setTitle("Pathfinding Game");
        this.setSize(1280, 720);
        
        canvas = new PathCanvas();
        this.add(canvas);
        
        this.setVisible(true);
    }
}
