package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathTick;

public class PathSprite extends PathObject {
    
    private Point pos;
    
    public void init() {
        pos = new Point(0, 0);
    }
    
    public void tick(PathTick pt) {
        if (pt.isUpPressed()) {
            pos.y -= 5;
        }
        if (pt.isDownPressed()) {
            pos.y += 5;
        }
        if (pt.isLeftPressed()) {
            pos.x -= 5;
        }
        if (pt.isRightPressed()) {
            pos.x += 5;
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(0+pos.x, 0+pos.y, 48, 48);
    }
    
    public void remove() {
        
    }
}
