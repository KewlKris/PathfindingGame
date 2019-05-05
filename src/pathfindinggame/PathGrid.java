package pathfindinggame;

import java.awt.*;

public class PathGrid {
    public static final int blockSize = 32;
    public static Point viewOffset = new Point(0, 0);
    
    public static final boolean[][] grid = new boolean[][]
    {
        
        {true, true, true, true, true},
        {true, false, false, false, true},
        {true, false, true, false, false},
        {true, true, true, true, true},
        {true, false, false, false, false}
        
        /*
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, true, true, true, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
        */
    };
    
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
