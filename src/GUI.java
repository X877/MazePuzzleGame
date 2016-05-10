import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


/**
 * Drawing boards with 2d graphic
 */
public class GUI extends JPanel implements KeyListener, ActionListener{
    private Board mazeBoard;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private boolean sPressed;
    private boolean wPressed;
    private boolean aPressed;
    private boolean dPressed;
    private Timer actionTimer;
    
    public GUI(Board mazeBoard) {
        this.mazeBoard = mazeBoard;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.actionTimer = new Timer(40, this);
        this.actionTimer.start();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        //x1, y1, x2, y2 coordinate
        for (int i = 0; i < 500; i = i+50) {
            g2d.drawLine(20+i, 20+i, i, 20+i);
            g2d.drawLine(20+i, 20+i, 20+i, i);
            g2d.drawLine(i, i, 20+i, i);
            g2d.drawLine(i, i, i, 20+i);

        }
        g2d.drawOval(x, y, 20, 20);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	// Dodgey and temporary
    	dy = 0;
    	if (sPressed){
    		dy += 5;
    	}
    	if (wPressed){
    		dy -= 5;
    	}
    	dx = 0;
    	if (dPressed){
    		dx += 5;
    	}
    	if (aPressed){
    		dx -= 5;
    	}
    	
    	
    	
        x += dx;
        y += dy;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Random rand = new Random();
        if (e.getKeyChar() == 's'){
            sPressed = true;
        }
        if (e.getKeyChar() == 'w'){
        	wPressed = true;
        }
        if (e.getKeyChar() == 'a'){
        	aPressed = true;
        }
        if (e.getKeyChar() == 'd'){
        	dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 's'){
        	sPressed = false;
        }
        if (e.getKeyChar() == 'w'){
        	wPressed = false;
        }
        if (e.getKeyChar() == 'a'){
        	aPressed = false;
        }
        if (e.getKeyChar() == 'd'){
        	dPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
}
