package pathfindinggame;

public class PathTick {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public PathTick(boolean up, boolean dp, boolean lp, boolean rp) {
        upPressed = up;
        downPressed = dp;
        leftPressed = lp;
        rightPressed = rp;
    }
}
