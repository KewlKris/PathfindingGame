package pathfindinggame.objects;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Comparator;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTargeter extends PathObject implements Runnable {
    private Point target;
    private PathHunter hunter;
    private PathPlayer player;
    private TraceStep destNode;
    
    private boolean pathIsComplete;
    
    public PathTargeter(PathPlayer pl, PathHunter ph) {
        player = pl;
        hunter = ph;
    }
    
    public void init() {
        Point hunterPos = hunter.getPos();
        target = new Point(hunterPos.x/PathGrid.blockSize, hunterPos.y/PathGrid.blockSize);
        isBusy = false;
        removing = false;
    }
    
    public void tick(PathTick pt) {
        Point playerPos = player.getPos();
        //target = new Point((int)(Math.round((playerPos.x + PathGrid.blockSize/2) / PathGrid.blockSize)), (int)(Math.round((playerPos.y + PathGrid.blockSize/2) / PathGrid.blockSize)));
        //hunter.setTarget(target);
        hunter.setRunning(player.getSearchRadius() < PathSettings.ALERT_RADIUS);
        assignTarget();
        Thread t = new Thread(this);
        t.start();
    }
    
    int targetUpdateDelay = 300;
    int targetCount = 0;
    private void assignTarget() {
        //Testing only
        if (false) {
            Point playerPos = player.getPos();
            target = new Point((int)(Math.round((playerPos.x + PathGrid.blockSize/2) / PathGrid.blockSize)), (int)(Math.round((playerPos.y + PathGrid.blockSize/2) / PathGrid.blockSize)));
            return;
        }
        //Testing only
        
        Point playerPos = player.getPos();
        int posX = playerPos.x + PathGrid.blockSize/2;
        int posY = playerPos.y + PathGrid.blockSize/2;
        
        double searchRadius = player.getSearchRadius();
        //Determine if target is outside of radius
        int targetX = target.x * PathGrid.blockSize + PathGrid.blockSize/2;
        int targetY = target.y * PathGrid.blockSize + PathGrid.blockSize/2;
        if (Math.sqrt(Math.pow(Math.abs(posX-targetX), 2) + Math.pow(Math.abs(posY-targetY), 2)) > searchRadius) {
            targetCount = targetUpdateDelay;
        }
        
        
        if (targetCount != targetUpdateDelay) {
            targetCount++;
            return;
        }
        targetCount = 0;
        
        
        int blockX, blockY;
        while (true) {
            int angle = (int)(Math.random() * 360);
            int distance = (int)(Math.random() * searchRadius+1);
            double dispX = Math.cos(angle) * (double)distance;
            double dispY = Math.sin(angle) * (double)distance;

            
            try {
                blockX = ((int)(Math.round(posX + dispX))) / PathGrid.blockSize;
                blockY = ((int)(Math.round(posY + dispY))) / PathGrid.blockSize;
                
                if (PathGrid.getGrid()[blockY][blockX]) {
                    continue;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
            break;
        }
        target = new Point(blockX, blockY);
    }
    
    int pathUpdateDelay = 1;
    int count = 0;
    private boolean removing = false;
    public void run() {
        if (count != pathUpdateDelay) {
            count++;
            return;
        }
        count = 0;
        if (isBusy) {
            return;
        }
        performAStar();
    }
    
    private ArrayList<TraceStep> openList;
    private ArrayList<TraceStep> closedList;
    private final int PATH_LIMIT = 500;
    private boolean isBusy;
    private void performAStar() {
        isBusy = true;
        
        
        openList = new ArrayList<TraceStep>();
        closedList = new ArrayList<TraceStep>();
        if (target == null) {
            return;
        }
        
        Point hunterPos = hunter.getPos();
        Point threadTarget = (Point)target.clone();
        int hunterBlockX = (int)(Math.round(hunterPos.x / PathGrid.blockSize));
        int hunterBlockY = (int)(Math.round(hunterPos.y / PathGrid.blockSize));
        
        if (threadTarget.x == hunterBlockX && threadTarget.y == hunterBlockY) {
            isBusy = false;
            return;
        }
        
        TraceStep startNode = new TraceStep(null, 0, 0, hunterBlockX, hunterBlockY);
        openList.add(startNode);
        //TraceStep nextNode = nextStep(startNode);
        
        while (openList.size() > 0) {
            if (openList.size() > PATH_LIMIT) {
                //Limit reached! Take best path...
                bailSearch();
                break;
            }
            openList.sort(Comparator.naturalOrder());
            /*
            TraceStep lowestF = openList.get(0);
            int lowestIndex = 0;
            for (int x=1; x<openList.size(); x++) {
                if (openList.get(x).f < lowestF.f) {
                    lowestF = openList.get(x);
                    lowestIndex = x;
                }
            }
            */
            
            //lowestF = openList.remove(lowestIndex);
            TraceStep lowestF = openList.remove(0);
            ArrayList<TraceStep> successors = new ArrayList<TraceStep>();
            
            boolean[][] grid = PathGrid.getGrid();
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
                if (threadTarget.x == suc.x && threadTarget.y == suc.y) {
                    //End search? Target found
                    targetFound = true;
                    destNode = suc;
                    pathIsComplete = true;
                    break;
                }
                //Search open list for same pos lower f
                int thisF = suc.f;
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
        //System.out.println(closedList.size());
        if (!removing) {
            hunter.setPath(new HunterPath(destNode));
        }
        isBusy = false;
    }
    
    /**
     * Finds the currently best scoring node and updates destNode
     */
    private void bailSearch() {
        TraceStep bestStep = openList.get(0);
        int lowestValue = bestStep.f;
        
        for (TraceStep ts : openList) {
            if (ts.compareTo(bestStep) < 0) {
                bestStep = ts;
                lowestValue = ts.f;
            }
        }
        
        destNode = bestStep;
        pathIsComplete = false;
        isBusy = false;
    }
    
    private int findH(int x, int y) {
        return Math.abs(x - target.x) + Math.abs(y - target.y);
    }
    
    
    
    
    public void draw(Graphics2D g) {
        if (PathSettings.DEBUG_ENABLED) {
            try {
                TraceStep step = destNode;
                Color c;
                if (pathIsComplete) {
                    c = Color.GREEN;
                } else {
                    c = new Color(100, 0, 0);
                }
                while (step.parent != null) {
                    colorBlock(g, step.x, step.y, c);
                    g.drawString(String.format("%d", step.f), step.x*PathGrid.blockSize, step.y*PathGrid.blockSize);
                    step = step.parent;
                }
            } catch (NullPointerException e) {
                
            }
            colorBlock(g, target.x, target.y, Color.YELLOW);
            
            //Draw searching radius
            double searchRadius = player.getSearchRadius();
            Point playerPos = player.getPos();
            //Draw default max
            g.setColor(Color.RED);
            int defMaxRadius = player.getDefaultMaxRadius();
            g.drawOval((playerPos.x+PathGrid.blockSize/2)-defMaxRadius, (playerPos.y+PathGrid.blockSize/2)-defMaxRadius, defMaxRadius*2, defMaxRadius*2);
            //Draw max
            g.setColor(new Color(100, 0, 0));
            int maxRadius = player.getMaxRadius();
            g.drawOval((playerPos.x+PathGrid.blockSize/2)-maxRadius, (playerPos.y+PathGrid.blockSize/2)-maxRadius, maxRadius*2, maxRadius*2);
            //Draw current
            g.setColor(Color.BLUE);
            g.drawOval((playerPos.x+PathGrid.blockSize/2)-(int)searchRadius, (playerPos.y+PathGrid.blockSize/2)-(int)searchRadius, (int)searchRadius*2, (int)searchRadius*2);
        }
    }
    
    private static void colorBlock(Graphics2D g, int x, int y, Color c) {
        g.setColor(c);
        g.drawRect(x*PathGrid.blockSize + PathGrid.blockSize/4, y*PathGrid.blockSize + PathGrid.blockSize/4, PathGrid.blockSize/2, PathGrid.blockSize/2);
    }
    
    public void drawGUI(Graphics2D g) {
        double alertLevel = player.getSearchRadius() / (double)player.getDefaultMaxRadius();
        Stroke stroke = new BasicStroke(2.0f);
        g.setStroke(stroke);
        g.setColor(new Color((int)((Math.abs(alertLevel-1)*255)), 0, 0));
        g.draw(new RoundRectangle2D.Double(20, 20, 200, 40, 10, 10));
        g.fill(new RoundRectangle2D.Double(20, 20, (int)(Math.abs(alertLevel-1)*200), 40, 10, 10));
    }
    
    public void remove() {
        removing = true;
        destNode = null;
    }
}

class TraceStep implements Comparable {
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
    
    public int compareTo(Object o) {
        TraceStep ts = (TraceStep)o;
        return f - ts.f;
    }
    
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

class HunterPath {
    private ArrayList<Point> path;
    public HunterPath(TraceStep destNode) {
        path = new ArrayList<Point>();
        try {
            TraceStep step = destNode;
            while (step.parent != null) {
                path.add(0, new Point(step.x, step.y));
                step = step.parent;
            }
            //path.add(0, new Point(step.x, step.y));
        } catch (NullPointerException e) {
            path = null;
        }
    }
    
    public Point currentPoint() {
        return path.get(0);
    }
    
    public Point popPoint() {
        if (path.size() == 0) {
            return null;
        }
        return path.remove(0);
    }
    
    public String toString() {
        return path.toString();
    }
}
