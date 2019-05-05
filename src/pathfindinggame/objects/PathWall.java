package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathTick;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;

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
        g.fillRect(coordPoint.x + PathGrid.viewOffset.x, coordPoint.y + PathGrid.viewOffset.y, PathGrid.blockSize, PathGrid.blockSize);
        
        //Draw Debug
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.RED);
            Point coordPos = PathGrid.toCoord(blockPos.x, blockPos.y);
            g.drawString(String.format("[%d]", this.id), coordPos.x + PathGrid.viewOffset.x, coordPos.y + PathGrid.viewOffset.y);
            //g.drawString(String.format("[%d] (%d, %d)", this.id, coordPos.x, coordPos.y), coordPos.x, coordPos.y);
        }
    }
    
    public void remove() {
        
    }
}
