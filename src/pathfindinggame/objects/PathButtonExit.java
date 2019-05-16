package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGame;

public class PathButtonExit extends PathButton {
    public PathButtonExit(Point p, Dimension d, int v) {
        super(p, d, v);
    }
    
    public void action() {
        System.exit(0);
    }
    
    public void draw(Graphics2D g) {
        super.draw(g);
        Color color;
        if (isHovering) {
            color = new Color(200, 0, 0);
        } else {
            color = new Color(100, 0, 0);
        }
        g.setColor(color);
        Font font = new Font("monospaced", Font.BOLD, 64);
        g.setFont(font);
        g.drawString("Exit", pos.x, pos.y);
    }
}