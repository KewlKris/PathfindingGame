package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTimer extends PathObject {
    private float duration;
    private boolean timedOut;
    private boolean isRunning;
    private long time;
    
    private boolean startOnInit;
    public PathTimer(float d, boolean s)
    {
        duration = d;
        startOnInit = s;
    }
    
    public boolean getIsRunning() {
        return isRunning;
    }
    
    public boolean getTimedOut() {
        return timedOut;
    }
    
    public float getDuration() {
        return duration;
    }
    
    public float getTime() {
        return ((float)time) / 1000f;
    }
    
    public void init() {
        reset();
        if (startOnInit) {
            start();
        }
    }
    
    public void start() {
        isRunning = true;
    }
    
    public void stop() {
        isRunning = false;
    }
    
    public void reset() {
        time = (long)(duration*1000);
        timedOut = false;
    }
    
    public void tick(PathTick pt) {
        if (isRunning && !timedOut) {
            time -= PathSettings.TICK_DELAY;
            if (time <= 0) {
                timedOut = true;
                isRunning = false;
                time = 0;
            }
        }
    }
    
    public void draw(Graphics2D g) {
        
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
