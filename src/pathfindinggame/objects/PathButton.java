package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathButton extends PathObject {
    protected Point pos;
    protected Dimension dim;
    protected boolean isHovering;
    private int verticalOffset;
    public PathButton(Point p, Dimension d, int v)
    {
        pos = p;
        dim = d;
        verticalOffset = v;
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
                y >= pos.y-verticalOffset &&
                y < pos.y + dim.height - verticalOffset);
        
        if (isHovering && pt.isLeftMousePressed()) {
            action();
        }
    }
    
    protected void action() {
        
    }
    
    public void draw(Graphics2D g) {
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.cyan);
            g.drawRect(pos.x, pos.y-verticalOffset, dim.width, dim.height);
        }
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
