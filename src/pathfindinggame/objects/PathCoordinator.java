package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathTick;
import java.text.DecimalFormat;
import pathfindinggame.PathGame;

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
        PathGrid.viewOffset = new Point(-500, -300);
    }
    
    public void tick(PathTick pt) {
        levelTimer.tick(pt);
        player.adjustMaxRadius(levelTimer.getTime()/levelTimer.getDuration());
        if (player.getBlockPos().equals(hunter.getBlockPos())) {
            PathGame.changeScene(PathGame.MAIN_MENU);
        }
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
