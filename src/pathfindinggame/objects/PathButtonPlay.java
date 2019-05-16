package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGame;

public class PathButtonPlay extends PathButton {
    public PathButtonPlay(Point p, Dimension d) {
        super(p, d);
    }
    
    public void action() {
        PathGame.changeScene(PathGame.LEVEL);
    }
}
