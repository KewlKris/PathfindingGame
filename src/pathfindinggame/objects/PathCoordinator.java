package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathTick;
import java.text.DecimalFormat;

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
    
    private DecimalFormat timeFormat = new DecimalFormat("0.0");
    public void drawGUI(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        String time = timeFormat.format(levelTimer.getTime());
        g.drawString(time, 1150, 52);
    }
    
    public void gameLost() {
        
    }
    
    public void remove() {
        
    }
}
