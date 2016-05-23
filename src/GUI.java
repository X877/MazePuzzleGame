import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;


/**
 * Drawing boards with 2d graphic
 */

public class GUI extends JPanel implements KeyListener, ActionListener{
    
    private int tileSize;
    private int posFromBottom;
    private int posFromLeft;
	public static final int tickTime = 20;
	private HashSet<Character>keysPressed;
	
	private Game game;
    private Timer actionTimer;
    
    public GUI(Board mazeBoard, int tileSize, int posFromBottom, int posFromLeft) {
        this.game = new Game(mazeBoard);
        this.tileSize = tileSize;
        this.posFromBottom = posFromBottom;
        this.posFromLeft = posFromLeft;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.actionTimer = new Timer(tickTime, this);
        this.actionTimer.start();
        keysPressed = new HashSet<Character>();
    }

    /**
     *
     * @param g
     */
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        Tiles currTile;

        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;

        for (int i = 0; i < game.getBoard().getColumns().size(); i++) {
            y1 = posFromBottom - 2 * tileSize;
            y2 = y1;

            x1 = posFromLeft + i * tileSize;

            for (int j = 0; j < game.getBoard().getColumns().get(i).size(); j++) {
                currTile = game.getBoard().getTile(i, j);

                //(x1, y1, x2, y2) coordinate format
                //North wall
                x2 = x1 + tileSize;
                if (currTile.isWall(Tiles.NORTH)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //East wall
                x1 += tileSize;
                y2 += tileSize;
                if (currTile.isWall(Tiles.EAST)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //South wall
                y1 += tileSize;
                x2 -= tileSize;
                if (currTile.isWall(Tiles.SOUTH)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //West wall
                x1 -= tileSize;
                y2 -= tileSize;
                if (currTile.isWall(Tiles.WEST)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //Change position upwards for next iteration
                y1 = y1 - 2 * tileSize;
                y2 = y1;

            }
        }
        
        Player player = game.getPlayer();
        int playerSize = (int) (tileSize*Game.playerSize+1);
        int playerDisplayX =(int)(posFromLeft+ Game.roundDouble2(player.getX())*tileSize)+1;
        int playerDisplayY = (int)(posFromBottom-Game.roundDouble2(player.getY())*tileSize-tileSize-playerSize);
        g2d.fillRect(playerDisplayX, playerDisplayY, playerSize, playerSize);
        int radius = 50;
        int x = playerDisplayX + playerSize/2;
        int y = playerDisplayY + playerSize/2;
        int diameter = 100;
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
        	      RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        Rectangle rect = new Rectangle(posFromLeft, posFromBottom - tileSize*game.getBoard().getHeight()-tileSize, game.getBoard().getWidth()*tileSize, tileSize*game.getBoard().getHeight());
        Ellipse2D spot = new Ellipse2D.Float(
                (float) x - (diameter / 2f),
                (float) y - (diameter / 2f),
                (float) diameter,
                (float) diameter);
        Area area = new Area(rect);
        area.subtract(new Area(spot));
        g2d.fill(area);
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	game.tick();
    	game.movePlayer(keysPressed);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	keysPressed.add(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	keysPressed.remove(e.getKeyChar());
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    
}
