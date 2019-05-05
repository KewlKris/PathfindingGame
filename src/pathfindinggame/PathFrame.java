package pathfindinggame;

import java.awt.*;
import java.awt.event.*;

public class PathFrame extends Frame {
    public PathCanvas canvas;
    public PathFrame() {
        this.setTitle("Pathfinding Game");
        this.setSize(PathSettings.GAME_RESOLUTION.width, PathSettings.GAME_RESOLUTION.height);
        
        canvas = new PathCanvas();
        this.add(canvas);
        
        this.setVisible(true);
    }
}
