package pathfindinggame;

import java.awt.*;
import java.awt.event.*;

public class PathCanvas extends Canvas {
    
    public static boolean upPressed = false;
    public static boolean downPressed = false;
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    
    public static boolean leftMousePressed = false;
    public static boolean rightMousePressed = false;
    public static int mouseX = 0;
    public static int mouseY = 0;
    
    private Image frameBuffer;
    private Graphics2D buf;
    public PathCanvas() {
        this.addKeyListener(new KeyboardPressListener());
        this.addMouseListener(new MousePressedListener());
        this.addMouseMotionListener(new MouseMovedListener());
        
        //this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        
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
        
        Dimension sceneDim = PathGame.getSceneDimension();
        Image sceneBuffer = createImage(sceneDim.width*PathGrid.blockSize, sceneDim.height*PathGrid.blockSize);
        Graphics2D sceneGraph = (Graphics2D)sceneBuffer.getGraphics();
        
        Image guiBuffer = createImage(PathSettings.GAME_RESOLUTION.width, PathSettings.GAME_RESOLUTION.height);
        Graphics2D GUIGraph = (Graphics2D)guiBuffer.getGraphics();
        
        sceneGraph.setColor(new Color(230, 230, 230));
        sceneGraph.fillRect(0, 0, sceneDim.width*PathGrid.blockSize, sceneDim.height*PathGrid.blockSize);
        
        PathGame.draw(sceneGraph);
        
        buf.drawImage(sceneBuffer, PathGrid.viewOffset.x, PathGrid.viewOffset.y, this);
        PathGame.drawGUI(buf);
        
        /*
        if (this.getWidth() >= this.getHeight()) {
            //Scale vertically place horizontally
            double scale = (double)this.getHeight()/(double)PathSettings.GAME_RESOLUTION.height;
            graph.scale(scale, scale);
            graph.drawImage(frameBuffer, (this.getWidth()/2-PathSettings.GAME_RESOLUTION.width)/2, 0, this);
        }
        */
        
        double scaleW = (double)this.getWidth()/(double)PathSettings.GAME_RESOLUTION.width;
        double scaleH = (double)this.getHeight()/(double)PathSettings.GAME_RESOLUTION.height;
        
        graph.scale(scaleW, scaleH);
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
    
    private class MousePressedListener implements MouseListener {
        public void mouseExited(MouseEvent e) {
            
        }
        
        public void mouseEntered(MouseEvent e) {
            
        }
        
        public void mouseReleased(MouseEvent e) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    leftMousePressed = false;
                    break;
                case MouseEvent.BUTTON2:
                    rightMousePressed = false;
                    break;
            }
        }
        
        public void mousePressed(MouseEvent e) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    leftMousePressed = true;
                    break;
                case MouseEvent.BUTTON2:
                    rightMousePressed = true;
                    break;
            }
        }
        
        public void mouseClicked(MouseEvent e) {
            
        }
    }
    
    private class MouseMovedListener implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
        
        public void mouseDragged(MouseEvent e) {
            
        }
    }
}
