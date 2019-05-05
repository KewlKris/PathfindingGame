package pathfindinggame;

import java.util.ArrayList;
import pathfindinggame.objects.*;

import java.awt.*;

public class PathGame {
    private static int idCounter = 0;
    public static int serveID() {
        return idCounter++;
    }
    
    public static void changeScene(int id) {
        sceneID = id;
        scenes[sceneID].init();
    }
    
    public static void startGame() {
        initializeScenes();
        changeScene(MAIN_MENU);
        startTicks();
        startDrawing();
    }
    
    private static void startTicks() {
        Thread ticker = new Thread(new TickTimer());
        ticker.start();
    }
    
    public static void tick(PathTick pt) {
        scenes[sceneID].tick(pt);
    }
    
    private static void startDrawing() {
        Thread drawer = new Thread(new DrawTimer());
        drawer.start();
    }
    
    public static void draw(Graphics2D g) {
        scenes[sceneID].draw(g);
    }
    
    private static PathScene[] scenes;
    private static int sceneID;
    private static final int MAIN_MENU = 0;
    private static void initializeScenes() {
        scenes = new PathScene[] {initMainMenu()};
    }
    
    private static PathScene initMainMenu() {
        PathScene scene = new PathScene();
        scene.addObject(new PathPlayer());
        return scene;
    }
    
    
}

class TickTimer implements Runnable {
    public boolean isRunning = true;
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PathGame.tick(new PathTick(PathCanvas.upPressed, PathCanvas.downPressed, PathCanvas.leftPressed, PathCanvas.rightPressed));
        }
    }
}

class DrawTimer implements Runnable {
    public boolean isRunning = true;
    public void run() {
        while (isRunning) {
            PathfindingGame.frame.canvas.repaint();
        }
    }
}