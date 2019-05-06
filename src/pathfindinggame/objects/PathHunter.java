package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathHunter extends PathObject {
    private Point pos;
    private Point startPos;
    private int speed = 0;
    private Point targetPos;
    private boolean atTarget;
    
    public boolean atTarget() {
        return atTarget;
    }
    
    public void setTarget(Point tPos) {
        targetPos = PathGrid.toCoord(tPos.x, tPos.y);
    }
    
    /**
     * Takes the position of the player as a PathPoint
     * @param pos 
     */
    public PathHunter(Point pos) {
        startPos = pos;
    }
    
    public Point getPos() {
        return pos;
    }
    
    public void init() {
        pos = new Point(startPos.x * PathGrid.blockSize, startPos.y * PathGrid.blockSize);
        targetPos = (Point)pos.clone();
    }
    
    public void tick(PathTick pt) {
        if (pos.equals(targetPos)) {
            atTarget = true;
        } else {
            atTarget = false;
            
            if (targetPos.x > pos.x) {
                pos.x += speed;
            }
            if (targetPos.x < pos.x) {
                pos.x -= speed;
            }
            if (targetPos.y > pos.y) {
                pos.y += speed;
            }
            if (targetPos.y < pos.y) {
                pos.y -= speed;
            }
            
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(new Color(120, 0, 0));
        g.fillRect(pos.x, pos.y, PathGrid.blockSize, PathGrid.blockSize);
        
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.RED);
            g.drawString("" + atTarget, pos.x, pos.y);
        }
    }
    
    public void remove() {
        
    }
}
