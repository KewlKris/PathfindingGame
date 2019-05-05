package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathPlayer extends PathObject {
    private Point pos;
    private Point startPos;
    private int speed = 5;
    
    /**
     * Takes the position of the player as a PathPoint
     * @param pos 
     */
    public PathPlayer(Point pos) {
        startPos = pos;
    }
    
    public void init() {
        pos = new Point(startPos.x * PathGrid.blockSize, startPos.y * PathGrid.blockSize);
    }
    
    public void tick(PathTick pt) {
        if (pt.isUpPressed()) {
            if (pos.y+PathGrid.viewOffset.y <= PathSettings.GAME_RESOLUTION.height/3) {
                PathGrid.viewOffset.y += speed;
            } else {
            }
            pos.y -= speed;
        }
        if (pt.isDownPressed()) {
            if (pos.y+PathGrid.viewOffset.y >= PathSettings.GAME_RESOLUTION.height - PathSettings.GAME_RESOLUTION.height/3) {
                PathGrid.viewOffset.y -= speed;
            } else {
            }
            pos.y += speed;
        }
        if (pt.isLeftPressed()) {
            if (pos.x+PathGrid.viewOffset.x <= PathSettings.GAME_RESOLUTION.width/4) {
                PathGrid.viewOffset.x += speed;
            } else {
            }
            pos.x -= speed;
        }
        if (pt.isRightPressed()) {
            if (pos.x+PathGrid.viewOffset.x >= PathSettings.GAME_RESOLUTION.width - PathSettings.GAME_RESOLUTION.width/4) {
                PathGrid.viewOffset.x -= speed;
            } else {
            }
            pos.x += speed;
        }
        
        //Adjust for collisions
        boolean[][] grid = PathGrid.GRID_1;
        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid[y].length; x++) {
                if (grid[y][x]) {
                    int bStartX = x * PathGrid.blockSize;
                    int bEndX = x * PathGrid.blockSize + PathGrid.blockSize;
                    int bStartY = y * PathGrid.blockSize;
                    int bEndY = y * PathGrid.blockSize + PathGrid.blockSize;

                    int pStartX = pos.x;
                    int pEndX = pos.x + PathGrid.blockSize;
                    int pStartY = pos.y;
                    int pEndY = pos.y + PathGrid.blockSize;

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
        g.setColor(Color.RED);
        g.fillRect(pos.x, pos.y, PathGrid.blockSize, PathGrid.blockSize);
    }
    
    public void remove() {
        
    }
}
