package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathTick;

public class PathCoordinator extends PathObject {
    private PathPlayer player;
    private PathHunter hunter;
    private PathItem[] items;
    private PathTimer levelTimer;
    public PathCoordinator(PathPlayer p, PathHunter h, PathItem[] i, float t)
    {
        player = p;
        hunter = h;
        items = i;
        levelTimer = new PathTimer(t, true);
    }
    
    public void init() {
        levelTimer.init();
    }
    
    public void tick(PathTick pt) {
        levelTimer.tick(pt);
        player.adjustMaxRadius(levelTimer.getTime()/levelTimer.getDuration());
    }
    
    public void draw(Graphics2D g) {
        
    }
    
    public void drawGUI(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString(String.format("%f", levelTimer.getTime()), 500, 10);
    }
    
    public void gameLost() {
        
    }
    
    public void remove() {
        
    }
}
