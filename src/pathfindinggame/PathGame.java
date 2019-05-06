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
    
    public static Dimension getSceneDimension() {
        return scenes[sceneID].getDimension();
    }
    
    public static void startGame() {
        PathGrid.fillGrids();
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
    public static final int MAIN_MENU = 0;
    private static void initializeScenes() {
        scenes = new PathScene[] {initMainMenu()};
    }
    
    private static PathScene initMainMenu() {
        PathScene scene = new PathScene(new Dimension(PathGrid.GRID_1[0].length, PathGrid.GRID_1.length));
        
        for (int y=0; y<PathGrid.GRID_1.length; y++) {
            for (int x=0; x<PathGrid.GRID_1[y].length; x++) {
                if (PathGrid.GRID_1[y][x]) scene.addObject(new PathWall(new Point(x, y)));
            }
        }
        
        PathPlayer player = new PathPlayer(new Point(5, 5));
        PathHunter hunter = new PathHunter(new Point(10, 10));
        
        scene.addObject(player);
        scene.addObject(hunter);
        scene.addObject(new PathTargeter(player, hunter));
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