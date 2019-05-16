package pathfindinggame.objects;

import java.awt.*;
import java.awt.image.*;
import pathfindinggame.PathTick;

public class PathSprite extends PathObject {
    private Point pos;
    private BufferedImage image;
    public PathSprite(Point p, BufferedImage i)
    {
        pos = p;
        image = i;
    }
    
    public void init() {
        
    }
    
    public void tick(PathTick pt) {
        
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(image, pos.x, pos.y, pathfindinggame.PathfindingGame.frame.canvas);
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
