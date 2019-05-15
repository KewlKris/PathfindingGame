package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;
import pathfindinggame.objects.PathTargeter;

public class PathHunter extends PathObject {
    private Point pos;
    private Point startPos;
    private int speed;
    private Point targetPos;
    private HunterPath path;
    private final int WALK_SPEED = 2, RUN_SPEED = 4;
    
    
    public void setPath(HunterPath hp) {
        path = hp;
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
        speed = 2;
    }
    
    public void setRunning(boolean isRunning) {
        if (isRunning) {
            speed = RUN_SPEED;
        } else {
            speed = WALK_SPEED;
        }
    }
    
    public void tick(PathTick pt) {
        if (pos.equals(targetPos)) {
            Point t;
            try {
                t = path.popPoint();
            } catch (NullPointerException e) {
                return;
            }
            if (t == null) {
                return;
            }
            targetPos = new Point(t.x * PathGrid.blockSize, t.y * PathGrid.blockSize);
        }
        
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
        
        //Make pixel-perfect corrections
        if (Math.abs(targetPos.x - pos.x) < speed) {
            pos.x = targetPos.x;
        }
        if (Math.abs(targetPos.y - pos.y) < speed) {
            pos.y = targetPos.y;
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(new Color(120, 0, 0));
        g.fillRect(pos.x, pos.y, PathGrid.blockSize, PathGrid.blockSize);
        
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.RED);
            g.drawString(String.valueOf(speed), pos.x, pos.y);
        }
        
        //Draw view
        /*
        for each ray
            for path of ray
                walk ray
                test for wall
                if not wall hit
                    make darker
                    draw pixel
                else    <-- (wall hit)
                    make solid red
                    draw pixel
                    break ray
                
        */
        double nearWidth = 50;
        double farWidth = 100;
        double rayLength = 75;
        
        double rayRatio = nearWidth/farWidth;
        
        for (double farX=0; farX<farWidth; farX++) {
            double nearX = farX*rayRatio;
            //double xSlope = () work!
        }
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
