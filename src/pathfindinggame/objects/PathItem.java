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
        //finish
    }
    
    public void init() {
        isCollected = false;
    }
    
    public void tick(PathTick pt) {
        
    }
    
    public void draw(Graphics2D g) {
        
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
