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
        x += dx;
        y += dy;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Random rand = new Random();
        if (e.getKeyChar() == 's'){
            dy = 5;
        }
        if (e.getKeyChar() == 'w'){
            dy = -5;
        }
        if (e.getKeyChar() == 'a'){
            dx = -5;
        }
        if (e.getKeyChar() == 'd'){
            dx = 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 's'){
            dy = 0;
        }
        if (e.getKeyChar() == 'w'){
            dy = 0;
        }
        if (e.getKeyChar() == 'a'){
            dx = 0;
        }
        if (e.getKeyChar() == 'd'){
            dx = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }


}
