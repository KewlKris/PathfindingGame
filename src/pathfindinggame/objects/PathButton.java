package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathTick;

public class PathButton extends PathObject {
    private Point pos;
    private Dimension dim;
    private boolean isHovering;
    public PathButton(Point p, Dimension d)
    {
        pos = p;
        dim = d;
    }
    
    public void init() {
        isHovering = false;
    }
    
    public void tick(PathTick pt) {
        int x = pt.getMouseX();
        int y = pt.getMouseY();
        isHovering = (
                x >= pos.x &&
                x < pos.x + dim.width &&
                y >= pos.y &&
                y < pos.y + dim.height);
        
        if (isHovering && pt.isLeftMousePressed()) {
            action();
        }
    }
    
    protected void action() {
        
    }
    
    public void draw(Graphics2D g) {
        Color color;
        if (!isHovering) {
            color = new Color(100, 100, 255);
        } else {
            color = new Color(150, 150, 255);
        }
        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
