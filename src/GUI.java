import javax.swing.*;
import java.awt.*;


/**
 * Drawing boards with 2d graphic
 */
public class GUI extends JPanel{

    public GUI() {
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        //x1, y1, x2, y2 coordinate
        for (int i = 0; i < 20; i++) {
            g2d.drawLine(20, 20, 50, 20);
            g2d.drawLine(20, 20, 20, 50);
            g2d.drawLine(50, 50, 20, 50);
            g2d.drawLine(50, 50, 50, 20);

        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }


}
