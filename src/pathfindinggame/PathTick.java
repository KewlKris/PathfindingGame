package pathfindinggame;

public class PathTick {
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    public PathTick(boolean up, boolean dp, boolean lp, boolean rp) {
        upPressed = up;
        downPressed = dp;
        leftPressed = lp;
        rightPressed = rp;
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
}
