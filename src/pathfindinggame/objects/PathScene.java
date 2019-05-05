package pathfindinggame.objects;

import java.util.ArrayList;
import java.awt.Graphics2D;

import pathfindinggame.PathTick;

public class PathScene extends PathObject {
    private ArrayList<PathObject> sceneObjects;
    
    public PathScene() {
        super();
        sceneObjects = new ArrayList<PathObject>();
    }
    public void addObject(PathObject o) {
        sceneObjects.add(o);
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
