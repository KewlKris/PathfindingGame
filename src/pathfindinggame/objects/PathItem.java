package pathfindinggame.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathItem extends PathObject {
    private boolean isCollected;
    private PathPlayer player;
    Point position;
    
    public PathItem(PathPlayer p, Point pos)
    {
        player = p;
        position = pos;
    }
    
    public void init() {
        isCollected = false;
    }
    
    public void tick(PathTick pt) {
        //Check for player collision
        //System.out.println(player.getBlockPos());
        if (player.getBlockPos().equals(position) && !isCollected) {
            isCollected = true;
        }
    }
    
    public void draw(Graphics2D g) {
        if (!isCollected) {
            g.setColor(Color.YELLOW);
            g.fillOval(position.x*PathGrid.blockSize+PathGrid.blockSize/4, position.y*PathGrid.blockSize+PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
        }
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
