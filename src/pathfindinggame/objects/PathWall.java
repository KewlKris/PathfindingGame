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
        g.setColor(Color.BLACK);
        Point coordPoint = PathGrid.toCoord(blockPos.x, blockPos.y);
        g.fillRect(coordPoint.x, coordPoint.y, PathGrid.blockSize, PathGrid.blockSize);
        
        //Draw Debug
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.RED);
            Point coordPos = PathGrid.toCoord(blockPos.x, blockPos.y);
            g.drawString(String.format("[%d]", this.id), coordPos.x, coordPos.y);
            //g.drawString(String.format("[%d] (%d, %d)", this.id, coordPos.x, coordPos.y), coordPos.x, coordPos.y);
        }
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
