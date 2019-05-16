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
        scenes[sceneID].remove();
        sceneID = id;
        PathGrid.viewOffset = new Point(0, 0);
        scenes[sceneID].init();
    }
    
    public static Dimension getSceneDimension() {
        return scenes[sceneID].getDimension();
    }
    
    public static void startGame() {
        PathResources.loadResources();
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
    
    public static void draw(Graphics2D objectGraphics) {
        scenes[sceneID].draw(objectGraphics);
    }
    
    public static void drawGUI(Graphics2D guiGraphics) {
        scenes[sceneID].drawGUI(guiGraphics);
    }
    
    private static PathScene[] scenes;
    private static int sceneID;
    public static final int MAIN_MENU = 0;
    public static final int LEVEL = 1;
    public static final int HOW = 2;
    private static void initializeScenes() {
        scenes = new PathScene[] {initMainMenu(), initLevel(), initHow()};
    }
    
    private static PathScene initMainMenu() {
        PathScene scene = new PathScene(new Dimension(40, 23)); //Roughly 1280 x 720
        
        scene.addObject(new PathSprite(new Point(0, 0), PathResources.MENU_BACKGROUND));
        scene.addObject(new PathButtonPlay(new Point(75, 250), new Dimension(153, 50), 40));
        scene.addObject(new PathButtonHow(new Point(75, 330), new Dimension(420, 50), 40));
        scene.addObject(new PathButtonExit(new Point(75, 410), new Dimension(155, 50), 40));
        scene.addObject(new PathMainMenuText());
        return scene;
    }
    
    private static PathScene initHow() {
        PathScene scene = new PathScene(new Dimension(40, 23)); //Roughly 1280 x 720
        
        scene.addObject(new PathSprite(new Point(0, 0), PathResources.MENU_BACKGROUND));
        scene.addObject(new PathSprite(new Point(0, 0), PathResources.HOW_TO));
        scene.addObject(new PathButtonBack(new Point(550, 680), new Dimension(150, 50), 40));
        
        return scene;
    }
    
    private static PathScene initLevel() {
        PathScene scene = new PathScene(new Dimension(PathGrid.getGrid()[0].length, PathGrid.getGrid().length));
        
        for (int y=0; y<PathGrid.getGrid().length; y++) {
            for (int x=0; x<PathGrid.getGrid()[y].length; x++) {
                if (PathGrid.getGrid()[y][x]) scene.addObject(new PathWall(new Point(x, y)));
            }
        }
        
        PathPlayer player = new PathPlayer(new Point(35, 21));
        PathHunter hunter = new PathHunter(new Point(10, 10), player);
        
        scene.addObject(player);
        scene.addObject(hunter);
        scene.addObject(new PathTargeter(player, hunter));
        scene.addObject(new PathItem(player, new Point(15, 15)));
        scene.addObject(new PathCoordinator(player, hunter, null, 300f));
        return scene;
    }
}

class TickTimer implements Runnable {
    public boolean isRunning = true;
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(PathSettings.TICK_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PathGame.tick(new PathTick(PathCanvas.upPressed, PathCanvas.downPressed, PathCanvas.leftPressed, PathCanvas.rightPressed,
                                       PathCanvas.leftMousePressed, PathCanvas.rightMousePressed, PathCanvas.mouseX, PathCanvas.mouseY));
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