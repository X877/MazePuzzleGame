import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;


/**
 * Drawing boards with 2d graphic
 */

public class GUI extends JPanel implements KeyListener, ActionListener{
	private static int TILE_SIZE = 20;
	private static int MAZE_BOTTOM = 550;
	private static int MAZE_LEFT = 215;
	private static int tickTime = 16;
	private HashSet<Character>keysPressed;
	
	private Game game;
    private Timer actionTimer;
    
    public GUI(Board mazeBoard) {
        this.game = new Game(mazeBoard);
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
            //Reserved space for start tile
            //if (i == 0) {
            //    y1 = 550 - TILE_SIZE;       //Start at the bottom of the frame
            //    y2 = y1;
            //} else {
                y1 = MAZE_BOTTOM - 2 * TILE_SIZE;
                y2 = y1;
            //}

            x1 = MAZE_LEFT + i * TILE_SIZE;

            for (int j = 0; j < game.getBoard().getColumns().get(i).size(); j++) {
                currTile = game.getBoard().getTile(i, j);

                //(x1, y1, x2, y2) coordinate format
                //North wall
                x2 = x1 + TILE_SIZE;
                if (currTile.isWall(Tiles.NORTH)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //East wall
                x1 += TILE_SIZE;
                y2 += TILE_SIZE;
                if (currTile.isWall(Tiles.EAST)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //South wall
                y1 += TILE_SIZE;
                x2 -= TILE_SIZE;
                if (currTile.isWall(Tiles.SOUTH)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //West wall
                x1 -= TILE_SIZE;
                y2 -= TILE_SIZE;
                if (currTile.isWall(Tiles.WEST)) {
                    g2d.drawLine(x1, y1, x2, y2);
                }

                //Change position upwards for next iteration
                y1 = y1 - 2 * TILE_SIZE;
                y2 = y1;

            }
        }
        Player player = game.getPlayer();
        int playerSize = (int) (TILE_SIZE*Game.playerSize+1);
        g2d.fillRect((int)(MAZE_LEFT+ player.getX()*TILE_SIZE), (int)(MAZE_BOTTOM-player.getY()*20-TILE_SIZE-playerSize), playerSize, playerSize);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
