package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathTick;
import pathfindinggame.PathGrid;

public class PathWall extends PathObject {
    private Point blockPos;
    
    public PathWall(Point bPos) {
        blockPos = bPos;
    }
    
    public void init() {
        
    }
    
    public void tick(PathTick pt) {
        
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        Point coordPoint = PathGrid.toCoord(blockPos.x, blockPos.y);
        g.fillRect(coordPoint.x, coordPoint.y, 48, 48);
    }
    
    public void remove() {
        
    }
}
