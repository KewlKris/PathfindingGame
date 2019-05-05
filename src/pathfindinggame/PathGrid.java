package pathfindinggame;

import java.awt.*;

public class PathGrid {
    public static final int blockSize = 32;
    public static Point viewOffset = new Point(0, 0);
    
    public static final boolean[][] GRID_1 = new boolean[50][50];
    
    public static void fillGrids() {
        for (int y=0; y<GRID_1.length; y++) {
            for (int x=0; x<GRID_1[y].length; x++) {
                if (x==0 || y == 0 || x == (GRID_1[y].length-1) || y == (GRID_1.length-1)) {
                    GRID_1[y][x] = true;
                    continue;
                }
                if (((int)(Math.random() * 5)) == 0) {
                    GRID_1[y][x] = true;
                }
            }
        }
    }
    
    /**
     * Converts a grid block's position to x and y coordinates
     * @param x x position
     * @param y y position
     * @return Coordinate point of object
     */
    public static Point toCoord(int x, int y) {
        return new Point(x * blockSize, y * blockSize);
    }
}
