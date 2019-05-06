package pathfindinggame.objects;

import java.util.ArrayList;
import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTargeter extends PathObject {
    private Point target;
    private PathHunter hunter;
    private PathPlayer player;
    
    public PathTargeter(PathPlayer pl, PathHunter ph) {
        player = pl;
        hunter = ph;
    }
    
    public void init() {
        target = new Point(15, 15);
    }
    
    public void tick(PathTick pt) {
        Point playerPos = player.getPos();
        target = new Point((int)(Math.round(playerPos.x / PathGrid.blockSize)), (int)(Math.round(playerPos.y / PathGrid.blockSize)));
        hunter.setTarget(target);
        performAStar();
    }
    
    private ArrayList<TraceStep> openList = new ArrayList<TraceStep>();
    private ArrayList<TraceStep> closedList = new ArrayList<TraceStep>();
    int pathUpdateDelay = 60;
    int count = 0;
    private void performAStar() {
        if (count != pathUpdateDelay) {
            count++;
            return;
        }
        count = 0;
        
        
        openList = new ArrayList<TraceStep>();
        closedList = new ArrayList<TraceStep>();
        if (target == null) {
            return;
        }
        
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
            ArrayList<TraceStep> successors = new ArrayList<TraceStep>();
            
            boolean[][] grid = PathGrid.GRID_1;
            if (!grid[lowestF.y-1][lowestF.x]) { //If empty
                TraceStep suc = new TraceStep(lowestF, lowestF.g + 1, findH(lowestF.x, lowestF.y - 1), lowestF.x, lowestF.y - 1);
                successors.add(suc);
            }
            if (!grid[lowestF.y+1][lowestF.x]) { //If empty
                TraceStep suc = new TraceStep(lowestF, lowestF.g + 1, findH(lowestF.x, lowestF.y + 1), lowestF.x, lowestF.y + 1);
                successors.add(suc);
            }
            if (!grid[lowestF.y][lowestF.x-1]) { //If empty
                TraceStep suc = new TraceStep(lowestF, lowestF.g + 1, findH(lowestF.x - 1, lowestF.y), lowestF.x - 1, lowestF.y);
                successors.add(suc);
            }
            if (!grid[lowestF.y][lowestF.x+1]) { //If empty
                TraceStep suc = new TraceStep(lowestF, lowestF.g + 1, findH(lowestF.x + 1, lowestF.y), lowestF.x + 1, lowestF.y);
                successors.add(suc);
            }
            
            boolean targetFound = false;
            for (TraceStep suc : successors) {
                if (target.x == suc.x && target.y == suc.y) {
                    //End search? Target found
                    targetFound = true;
                    break;
                }
                //Search open list for same pos lower f
                int thisF = suc .f;
                boolean skip = false;
                for (TraceStep ts : openList) {
                    if (ts.f < thisF && ts.x == suc.x && ts.y == suc.y) {
                        skip = true;
                    }
                }
                if (skip) {
                    continue;
                }
                
                //Search closed list for same pos lower f
                thisF = suc.f;
                skip = false;
                for (TraceStep ts : closedList) {
                    if (ts.f < thisF && ts.x == suc.x && ts.y == suc.y) {
                        skip = true;
                    }
                }
                if (skip) {
                    continue;
                }
                
                openList.add(suc);
            }
            closedList.add(lowestF);
            if (targetFound) {
                break;
            }
        }
        System.out.println(closedList.size());
        
    }
    
    private int findH(int x, int y) {
        return Math.abs(x - target.x) + Math.abs(y - target.y);
    }
    
    
    
    
    public void draw(Graphics2D g) {
        if (PathSettings.DEBUG_ENABLED) {
            g.setColor(Color.YELLOW);
            Point blockPos = PathGrid.toCoord(target.x, target.y);
            g.drawRect(blockPos.x + PathGrid.blockSize/4, blockPos.y + PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
            
            for (TraceStep ts : closedList) {
                g.setColor(Color.GREEN);
                g.drawRect(ts.x*PathGrid.blockSize + PathGrid.blockSize/4, ts.y*PathGrid.blockSize + PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
                g.drawString(String.format("%d", ts.f), ts.x*PathGrid.blockSize, ts.y*PathGrid.blockSize);
            }
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
    
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    
    
}