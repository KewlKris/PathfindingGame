package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathPlayer extends PathObject {
    private Point pos;
    private Point startPos;
    private int speed = 3;
    private double searchRadius;
    private final int DEFAULT_MAX_RADIUS = 800;
    private int MAX_RADIUS;
    private int size = 32;
    
    public int getSize() {
        return size;
    }
    
    public int getDefaultMaxRadius() {
        return DEFAULT_MAX_RADIUS;
    }
    
    public int getMaxRadius() {
        return MAX_RADIUS;
    }
    
    public void adjustMaxRadius(float ratio) {
        MAX_RADIUS = (int)(ratio * (float)DEFAULT_MAX_RADIUS);
        normalizeSearchRadius();
    }
    
    public Point getPos() {
        return pos;
    }
    
    public Point getBlockPos() {
        int xValue = (pos.x+size/2) / PathGrid.blockSize;
        int yValue = (pos.y+size/2) / PathGrid.blockSize;
        
        return new Point(xValue, yValue);
    }
    
    /**
     * Takes the position of the player as a PathPoint
     * @param pos 
     */
    public PathPlayer(Point pos) {
        startPos = pos;
    }
    
    public void init() {
        pos = new Point(startPos.x * PathGrid.blockSize, startPos.y * PathGrid.blockSize);
        searchRadius = DEFAULT_MAX_RADIUS;
        MAX_RADIUS = DEFAULT_MAX_RADIUS;
    }
    
    private final double movementPenalty = -1d;
    private final double recoveryRate = 0.75d;
    public void tick(PathTick pt) {
        if (pt.isUpPressed()) {
            if (pos.y+PathGrid.viewOffset.y <= PathSettings.GAME_RESOLUTION.height/3) {
                PathGrid.viewOffset.y += speed;
            }
            pos.y -= speed;
            adjustSearchRadius(movementPenalty);
        }
        if (pt.isDownPressed()) {
            if (pos.y+PathGrid.viewOffset.y >= PathSettings.GAME_RESOLUTION.height - PathSettings.GAME_RESOLUTION.height/3) {
                PathGrid.viewOffset.y -= speed;
            }
            pos.y += speed;
            adjustSearchRadius(movementPenalty);
        }
        if (pt.isLeftPressed()) {
            if (pos.x+PathGrid.viewOffset.x <= PathSettings.GAME_RESOLUTION.width/4) {
                PathGrid.viewOffset.x += speed;
            }
            pos.x -= speed;
            adjustSearchRadius(movementPenalty);
        }
        if (pt.isRightPressed()) {
            if (pos.x+PathGrid.viewOffset.x >= PathSettings.GAME_RESOLUTION.width - PathSettings.GAME_RESOLUTION.width/4) {
                PathGrid.viewOffset.x -= speed;
            }
            pos.x += speed;
            adjustSearchRadius(movementPenalty);
        }
        adjustSearchRadius(recoveryRate);
        
        //Adjust for collisions
        boolean[][] grid = PathGrid.getGrid();
        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid[y].length; x++) {
                if (grid[y][x]) {
                    int bStartX = x * PathGrid.blockSize;
                    int bEndX = x * PathGrid.blockSize + PathGrid.blockSize;
                    int bStartY = y * PathGrid.blockSize;
                    int bEndY = y * PathGrid.blockSize + PathGrid.blockSize;

                    int pStartX = pos.x;
                    int pEndX = pos.x + size;
                    int pStartY = pos.y;
                    int pEndY = pos.y + size;

                    boolean isAbove = pStartY < bStartY;
                    boolean isLeft = pStartX < bStartX;

                    boolean verticalCol = (pStartY < bEndY && pEndY > bStartY);
                    boolean horizontalCol = (pStartX < bEndX && pEndX > bStartX);

                    if (verticalCol && horizontalCol) {
                        //System.out.println(String.format("(%d, %d) ", x, y) + Math.random());
                        if (Math.abs(pStartX - bStartX) > Math.abs(pStartY - bStartY)) {
                            if (isLeft) {
                                pos.x -= pEndX - bStartX;
                            } else {
                                pos.x += bEndX - pStartX;
                            }
                        } else {
                            if (isAbove) {
                                pos.y -= pEndY - bStartY;
                            } else {
                                pos.y += bEndY - pStartY;
                            }
                        }
                    }
                }
                
            }
        }
        
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(pos.x, pos.y, size, size);
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
    
    public void adjustSearchRadius(double d) {
        searchRadius += d;
        normalizeSearchRadius();
    }
    
    private void normalizeSearchRadius() {
        if (searchRadius < 0) {
            searchRadius = 0;
        } else if (searchRadius > MAX_RADIUS) {
            searchRadius = MAX_RADIUS;
        }
    }
    
    public double getSearchRadius() {
        return searchRadius;
    }
}
