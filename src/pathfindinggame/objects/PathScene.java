package pathfindinggame.objects;

import java.util.ArrayList;
import java.awt.*;

import pathfindinggame.PathTick;

public class PathScene extends PathObject {
    private ArrayList<PathObject> sceneObjects;
    private Dimension viewSize;
    
    public PathScene(Dimension view) {
        super();
        sceneObjects = new ArrayList<PathObject>();
        viewSize = view;
    }
    public void addObject(PathObject o) {
        sceneObjects.add(o);
    }
    
    public Dimension getDimension() {
        return viewSize;
    }
    
    @Override
    public void init() {
        for (PathObject o : sceneObjects) {
            o.init();
        }
    }
    
    @Override
    public void tick(PathTick pt) {
        for (PathObject o : sceneObjects) {
            o.tick(pt);
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        for (PathObject o : sceneObjects) {
            o.draw(g);
        }
    }
    
    @Override
    public void remove() {
        for (PathObject o : sceneObjects) {
            o.remove();
        }
    }
    
    
}
