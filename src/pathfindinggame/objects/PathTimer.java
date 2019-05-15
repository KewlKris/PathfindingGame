package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathGrid;
import pathfindinggame.PathSettings;
import pathfindinggame.PathTick;

public class PathTimer extends PathObject {
    private boolean startOnInit;
    private TimerThread thread;
    private float duration;
    
    
    public PathTimer(float d, boolean s)
    {
        duration = d;
        startOnInit = s;
    }
    
    public void init() {
        reset();
    }
    
    public void reset() {
        if (thread != null) {
            thread.endThread();
        }
        thread = new TimerThread(duration, startOnInit);
        thread.start();
    }
    
    public void startThread() {
        thread.startThread();
    }
    
    public void stopThread() {
        thread.stopThread();
    }
    
    public boolean getIsRunning() {
        return thread.getIsRunning();
    }
    
    public boolean getTimedOut() {
        return thread.getTimedOut();
    }
    
    public float getDuration() {
        return thread.getDuration();
    }
    
    public float getTime() {
        return thread.getTime();
    }
    
    public void tick(PathTick pt) {
        
    }
    
    public void draw(Graphics2D g) {
        
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}

class TimerThread extends Thread {
    private float duration;
    private boolean timedOut;
    private boolean isRunning;
    private long time;
    private boolean exitThread;
    
    public TimerThread(float d, boolean run)
    {
        duration = d;
        
        time = (long)(duration*1000);
        timedOut = false;
        
        isRunning = run;
        exitThread = false;
    }
    
    public void run() {
        try {
            while (!exitThread) {
                if (!isRunning) {
                    Thread.sleep(PathSettings.TICK_DELAY);
                    continue;
                }
                Thread.sleep(PathSettings.TICK_DELAY);
                if (isRunning && !timedOut) {
                    time -= PathSettings.TICK_DELAY;
                    if (time <= 0) {
                        timedOut = true;
                        isRunning = false;
                        time = 0;
                    }
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
    
    public void startThread() {
        isRunning = true;
    }
    
    public void stopThread() {
        isRunning = false;
    }
    
    public void endThread() {
        exitThread = true;
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
}
