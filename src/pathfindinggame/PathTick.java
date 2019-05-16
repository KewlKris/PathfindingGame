package pathfindinggame;

public class PathTick {
    private boolean upPressed, downPressed, leftPressed, rightPressed,
            leftMousePressed, rightMousePressed;
    private int xPos, yPos;
    public PathTick(boolean up, boolean dp, boolean lp, boolean rp, boolean lMP, boolean rMP, int x, int y) {
        upPressed = up;
        downPressed = dp;
        leftPressed = lp;
        rightPressed = rp;
        leftMousePressed = lMP;
        rightMousePressed = rMP;
        xPos = x;
        yPos = y;
    }
    
    public boolean isUpPressed() {
        return upPressed;
    }
    
    public boolean isDownPressed() {
        return downPressed;
    }
    
    public boolean isLeftPressed() {
        return leftPressed;
    }
    
    public boolean isRightPressed() {
        return rightPressed;
    }
    
    public boolean isLeftMousePressed() {
        return leftMousePressed;
    }
    
    public boolean isRightMousePressed() {
        return rightMousePressed;
    }
    
    public int getMouseX() {
        return xPos;
    }
    
    public int getMouseY() {
        return yPos;
    }
}
