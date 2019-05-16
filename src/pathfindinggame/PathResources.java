package pathfindinggame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class PathResources {
    public static BufferedImage MENU_BACKGROUND, HOW_TO, MAP;
    
    public static void loadResources() {
        try {
            MENU_BACKGROUND = ImageIO.read(new File("background.png"));
            HOW_TO = ImageIO.read(new File("howto.png"));
            MAP = ImageIO.read(new File("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
