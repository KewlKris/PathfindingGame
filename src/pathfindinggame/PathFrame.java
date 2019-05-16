package pathfindinggame;

import java.awt.*;
import java.awt.event.*;

public class PathFrame extends Frame {
    public PathCanvas canvas;
    public PathFrame() {
        this.setTitle("Operation: A* By Christopher Chamberlain");
        this.setSize(PathSettings.GAME_RESOLUTION.width, PathSettings.GAME_RESOLUTION.height);
        
        canvas = new PathCanvas();
        this.add(canvas);
        this.addWindowListener(new WindowClosingListener());
        
        this.setVisible(true);
    }
    
    private class WindowClosingListener implements WindowListener {
        public void windowOpened(WindowEvent we) {
            
        }
        
        public void windowClosing(WindowEvent we) {
            System.exit(0);
        }
        
        public void windowClosed(WindowEvent we) {
            
        }
        
        public void windowIconified(WindowEvent we) {
            
        }
        
        public void windowDeiconified(WindowEvent we) {
            
        }
        
        public void windowActivated(WindowEvent we) {
            
        }
        
        public void windowDeactivated(WindowEvent we) {
            
        }
    }
}
