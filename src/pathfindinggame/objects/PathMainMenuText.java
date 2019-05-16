package pathfindinggame.objects;

import java.awt.*;
import pathfindinggame.PathTick;

public class PathMainMenuText extends PathObject {
    private final String endString = "Operation: A*";
    private String string;
    private int index;
    
    public void init() {
        string = "";
        index = 0;
        count = 1;
    }
    
    private int count;
    public void tick(PathTick pt) {
        if (count != 0) {
            count = (count + 1) % 8;
            return;
        }
        count++;
        
        if (string.length() < endString.length()) {
            string += endString.substring(index, index+1);
            index++;
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        Font font = new Font("monospaced", Font.BOLD, 96);
        g.setFont(font);
        g.drawString(string, 50, 120);
        
        font = new Font("monospaced", Font.PLAIN, 16);
        g.setFont(font);
        g.drawString("Made by Christopher Chamberlain and written entirely in Java.", 320, 650);
    }
    
    public void drawGUI(Graphics2D g) {
        
    }
    
    public void remove() {
        
    }
}
