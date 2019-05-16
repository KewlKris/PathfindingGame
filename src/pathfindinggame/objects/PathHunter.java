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
    
    private PathTimer lookTimer;
    private PathPlayer player;
    
    
    public void setPath(HunterPath hp) {
        path = hp;
    }
    
    public void setTarget(Point tPos) {
        targetPos = PathGrid.toCoord(tPos.x, tPos.y);
    }
    
    public Point getBlockPos() {
        int xValue = (pos.x+PathGrid.blockSize/2) / PathGrid.blockSize;
        int yValue = (pos.y+PathGrid.blockSize/2) / PathGrid.blockSize;
        
        return new Point(xValue, yValue);
    }
    
    /**
     * Takes the position of the player as a PathPoint
     * @param pos 
     */
    public PathHunter(Point pos, PathPlayer p) {
        startPos = pos;
        player = p;
    }
    
    public Point getPos() {
        return pos;
    }
    
    public void init() {
        pos = new Point(startPos.x * PathGrid.blockSize, startPos.y * PathGrid.blockSize);
        targetPos = (Point)pos.clone();
        speed = 2;
        viewAngle = 90;
        lookingOffset = 0;
        
        lookTimer = new PathTimer(0.2f, true);
        lookTimer.init();
    }
    
    public void setRunning(boolean isRunning) {
        if (isRunning) {
            speed = RUN_SPEED;
        } else {
            speed = WALK_SPEED;
        }
    }
    
    public void tick(PathTick pt) {
        //Adjust view
        if (lookTimer.getTimedOut()) {
            lookingOffset = Math.random()*10 - 5;
            lookTimer.reset();
        }
        
        if (testRays()) {
            player.adjustSearchRadius(-10);
        }
        
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
            viewAngle = 270;
        }
        if (targetPos.x < pos.x) {
            pos.x -= speed;
            viewAngle = 90;
        }
        if (targetPos.y > pos.y) {
            pos.y += speed;
            viewAngle = 180;
        }
        if (targetPos.y < pos.y) {
            pos.y -= speed;
            viewAngle = 0;
        }
        
        //Make pixel-perfect corrections
        if (Math.abs(targetPos.x - pos.x) < speed) {
            pos.x = targetPos.x;
        }
        if (Math.abs(targetPos.y - pos.y) < speed) {
            pos.y = targetPos.y;
        }
        
    }
    
    private double viewAngle;
    private double lookingOffset;
    public void draw(Graphics2D g) {
        drawRays(g);
        g.setColor(new Color(120, 0, 0));
        g.fillRect(pos.x, pos.y, PathGrid.blockSize, PathGrid.blockSize);
        
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.RED);
            g.drawString(String.valueOf(speed), pos.x, pos.y);
        }
    }
    
    double farWidth = 200;
    double rayLength = 125;
    private boolean testRays() {
        int collisionCount = 0;
        double angle = ((viewAngle + lookingOffset)/180d) * Math.PI;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        for (double farX=-farWidth/2; farX<farWidth/2; farX++) {
            
            double destXS = 0;
            double destXE = (cos*farX + (-sin)*rayLength);
            double destYS = 0;
            double destYE = (sin*farX + cos*rayLength);
            
            
            
            double xSlope = (destXE-destXS)/rayLength;
            double ySlope = (0-destYE)/rayLength;
            
            double pixX = 0;
            double pixY = 0;
            for (int x=0; x<rayLength; x++) {
                pixX += xSlope;
                pixY += ySlope;
                
                //Test for block
                int norX = (int)(Math.round(pixX)) + pos.x + PathGrid.blockSize/2;
                int norY = (int)(Math.round(pixY)) + pos.y + PathGrid.blockSize/2;
                int gridX = norX/PathGrid.blockSize;
                int gridY = norY/PathGrid.blockSize;
                if (PathGrid.getGrid()[gridY][gridX]) {
                    //There is a collision
                    break;
                }
                Point playerPos = player.getPos();
                if (norX >= playerPos.x && norX < playerPos.x+player.getSize() && norY >= playerPos.y && norY < playerPos.y+player.getSize()) { //Adjust to test for player intersection
                    //There is a collision
                    collisionCount++;
                    break;
                }
                //Keep going 
            }
            
        }
        if (collisionCount > 5) {
            return true;
        }
        return false;
    }
    
    private void drawRays(Graphics2D g) {
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
        double startColor = 255;
        double endColor = 0;
        double colorSlope = (endColor-startColor)/rayLength;
        double color;
        
        double angle = ((viewAngle + lookingOffset)/180d) * Math.PI;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        for (double farX=-farWidth/2; farX<farWidth/2; farX++) {
            
            double destXS = 0;
            double destXE = (cos*farX + (-sin)*rayLength);
            double destYS = 0;
            double destYE = (sin*farX + cos*rayLength);
            
            
            
            double xSlope = (destXE-destXS)/rayLength;
            double ySlope = (0-destYE)/rayLength;
            
            double pixX = 0;
            double pixY = 0;
            color = startColor;
            for (int x=0; x<rayLength; x++) {
                pixX += xSlope;
                pixY += ySlope;
                
                //Test for block
                int norX = (int)(Math.round(pixX)) + pos.x + PathGrid.blockSize/2;
                int norY = (int)(Math.round(pixY)) + pos.y + PathGrid.blockSize/2;
                int gridX = norX/PathGrid.blockSize;
                int gridY = norY/PathGrid.blockSize;
                if (PathGrid.getGrid()[gridY][gridX]) {
                    //There is a collision
                    g.setPaint(Color.RED);
                    g.drawLine(norX, norY, norX, norY);
                    break;
                } else {
                    //Keep going
                    //System.out.println(color);
                    g.setPaint(new Color((int)Math.round(color), 0, 0));
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
        path = null;
    }
}
