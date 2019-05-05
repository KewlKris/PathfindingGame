package pathfindinggame.objects;

import java.awt.Graphics2D;
import pathfindinggame.PathGame;
import pathfindinggame.PathTick;

public abstract class PathObject {
    private int id;
    PathObject() {
        id = PathGame.serveID();
    }
    
    public int getID() {
        return id;
    }
    
    abstract void init();
    abstract void tick(PathTick pt);
    abstract void draw(Graphics2D g);
    abstract void remove();
}
