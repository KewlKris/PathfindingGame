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
        double farWidth = 200;
        double rayLength = 75;
        double startColor = 255;
        double endColor = 0;
        double colorSlope = (endColor-startColor)/rayLength;
        double color;
        
        double rayRatio = nearWidth/farWidth;
        
        double angle = 60;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        for (double farX=-farWidth/2; farX<farWidth/2; farX++) {
            double nearX = farX*rayRatio;
            
            double destXS = (cos*nearX + -sin*rayLength); //?
            double destXE = farX;
            double destYS = 0;
            double destYE = rayLength;
            
            
            
            double xSlope = (farX-nearX)/rayLength;
            
            double pixX = 0;
            double pixY = 0;
            color = startColor;
            for (int x=0; x<rayLength; x++) {
                pixX += xSlope;
                pixY = x;
                
                //Test for block
                int norX = (int)(Math.round(pixX)) + pos.x + PathGrid.blockSize/2;
                int norY = (int)(Math.round(pixY)) + pos.y + PathGrid.blockSize/2;
                int gridX = norX/PathGrid.blockSize;
                int gridY = norY/PathGrid.blockSize;
                if (PathGrid.GRID_1[gridY][gridX]) {
                    //There is a collision
                    g.setColor(Color.RED);
                    g.drawLine(norX, norY, norX, norY);
                    break;
                } else {
                    //Keep going
                    //System.out.println(color);
                    g.setColor(new Color((int)Math.round(color), 0, 0));
                    color += colorSlope;
                    if (color < 0) color = 0;
                    g.drawLine(norX, norY, norX, norY);
                }
                
            }
            
        }
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
