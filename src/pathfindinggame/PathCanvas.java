package pathfindinggame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PathCanvas extends Canvas {
    
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    
    private Image frameBuffer;
    private Graphics2D buf;
    public PathCanvas() {
        this.addKeyListener(new KeyboardPressListener());
        this.setBackground(Color.BLACK);
    }
    
    public void update(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        
        while (frameBuffer == null) {
            try {
                frameBuffer = createImage(PathSettings.GAME_RESOLUTION.width, PathSettings.GAME_RESOLUTION.height);
                buf = (Graphics2D)frameBuffer.getGraphics();
            } catch (NullPointerException e) {
                return;
            }
        }
        
        buf.setColor(Color.LIGHT_GRAY);
        buf.fillRect(0, 0, PathSettings.GAME_RESOLUTION.width, PathSettings.GAME_RESOLUTION.height);
        PathGame.draw(buf);
        
        int wDif = this.getWidth() - PathSettings.GAME_RESOLUTION.width;
        int hDif = this.getHeight() - PathSettings.GAME_RESOLUTION.height;
        
        double scale;
        if (wDif < hDif) {
            scale = (double)this.getWidth() / (double)PathSettings.GAME_RESOLUTION.width;
        } else {
            scale = (double)this.getHeight() / (double)PathSettings.GAME_RESOLUTION.height;
        }
        
        //graph.scale(scale, scale);
        graph.drawImage(frameBuffer, 0, 0, this);
        //graph.drawImage(frameBuffer, (this.getWidth()-PathSettings.GAME_RESOLUTION.width)/2, 0, this);
    }
    
    private class KeyboardPressListener implements KeyListener {
        public void keyReleased(KeyEvent e) {
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_UP:
                    upPressed = false;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = false;
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = false;
                    break;
            }
        }
        
        public void keyPressed(KeyEvent e) {
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    break;
            }
        }
        
        public void keyTyped(KeyEvent e) {
            
        }
    }
}
