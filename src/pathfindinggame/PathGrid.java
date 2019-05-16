package pathfindinggame;

import java.awt.*;

public class PathGrid {
    public static final int blockSize = 32;
    public static Point viewOffset = new Point(0, 0);
    
    public static final int GRID_ONE = 0;
    
    private static int gridID = GRID_ONE;
    private static final boolean[][] GRID_1 = new boolean[50][80];
    
    private static boolean[][][] GRIDS = new boolean[][][] {GRID_1};
    
    public static boolean[][] getGrid() {
        return GRIDS[gridID];
    }
    
    public static void setGrid(int id) {
        gridID = id;
    }
    
    public static void fillGrids() {
        for (int y=0; y<50; y++) {
            for (int x=0; x<80; x++) {
                //System.out.println(x + " " + y);
                if (PathResources.MAP.getRGB(x, y) != 0xFF_FF_FF_FF) {
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
