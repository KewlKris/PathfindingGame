package pathfindinggame;

import java.util.ArrayList;
import pathfindinggame.objects.PathScene;

public class PathGame {
    private static int idCounter = 0;
    public static int serveID() {
        return idCounter++;
    }
    
    private static PathScene[] scenes;
    private static int sceneID;
    private static final int MAIN_MENU = 0;
    public static void initializeScenes() {
        scenes = new PathScene[] {initMainMenu()};
    }
    
    private static PathScene initMainMenu() {
        PathScene scene = new PathScene();
        return scene;
    }
    
    
}