import javax.swing.*;
import java.awt.*;


/**
 * Drawing boards with 2d graphic
 */
public class GUI extends JPanel{
    private Board mazeBoard;

    public GUI(Board mazeBoard) {
        this.mazeBoard = mazeBoard;
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
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }


}
