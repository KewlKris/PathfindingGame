package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTargeter extends PathObject {
    private Point target;
    private PathHunter hunter;
    
    public PathTargeter(PathHunter ph) {
        hunter = ph;
    }
    
    public void init() {
        target = new Point(15, 15);
    }
    
    public void tick(PathTick pt) {
        hunter.setTarget(target);
    }
    
    public void draw(Graphics2D g) {
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.YELLOW);
            Point blockPos = PathGrid.toCoord(target.x, target.y);
            g.drawRect(blockPos.x + PathGrid.blockSize/4, blockPos.y + PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
        }
    }
    
    public void remove() {
        
    }
}
