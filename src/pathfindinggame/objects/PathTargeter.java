package pathfindinggame.objects;

import java.util.ArrayList;
import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTargeter extends PathObject {
    private Point target;
    private PathHunter hunter;
    
    public PathTargeter(PathHunter ph) {
        hunter = ph;
    }
    
    public void init() {
        target = new Point(15, 15);
    }
    
    public void tick(PathTick pt) {
        hunter.setTarget(target);
    }
    
    private ArrayList<TraceStep> openList = new ArrayList<TraceStep>();
    private ArrayList<TraceStep> closedList = new ArrayList<TraceStep>();
    private void performAStar() {
        
        Point hunterPos = hunter.getPos();
        int hunterBlockX = (int)(Math.round(hunterPos.x / PathGrid.blockSize));
        int hunterBlockY = (int)(Math.round(hunterPos.y / PathGrid.blockSize));
        
        TraceStep startNode = new TraceStep(null, 0, 0, hunterBlockX, hunterBlockY);
        openList.add(startNode);
        //TraceStep nextNode = nextStep(startNode);
        
        while (openList.size() > 0) {
            TraceStep lowestF = openList.get(0);
            int lowestIndex = 0;
            for (int x=1; x<openList.size(); x++) {
                if (openList.get(x).f < lowestF.f) {
                    lowestF = openList.get(x);
                    lowestIndex = x;
                }
            }
            
            lowestF = openList.remove(lowestIndex);
            TraceStep[] successors = new TraceStep[4];
            
            boolean[][] grid = PathGrid.GRID_1;
            if (!grid[lowestF.y-1][lowestF.x]) { //If empty
                
                TraceStep suc = new TraceStep(lowestF, lowestF.g + 1, findH(lowestF.x, lowestF.y - 1), lowestF.x, lowestF.y - 1);
            }
            
        }
        
    }
    
    private int findH(int x, int y) {
        return Math.abs(x - target.x) + Math.abs(y - target.y);
    }
    
    
    
    
    public void draw(Graphics2D g) {
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.YELLOW);
            Point blockPos = PathGrid.toCoord(target.x, target.y);
            g.drawRect(blockPos.x + PathGrid.blockSize/4, blockPos.y + PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
        }
    }
    
    public void remove() {
        
    }
}

class TraceStep {
    int f; //Total cost
    int g; //Path length
    int h; //Distance to goal
    int x;
    int y;
    
    TraceStep parent;
    
    public TraceStep(TraceStep p, int g, int h, int x, int y) {
        this.g = g;
        this.h = h;
        this.f = g + h;
        parent = p;
        this.x = x;
        this.y = y;
    }
    
    
}