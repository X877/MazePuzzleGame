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
	private static int TILE_SIZE = 20;
	private static int SOME_NUMBER = 550;
	private static int SOME_NUMBER2 = 225;
	private static int SQUARE_SIZE = 10;
	
    private Board mazeBoard;
    private int x;
    private int y;
    

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
        this.x = SOME_NUMBER2 + 5 + TILE_SIZE;
        this.y = SOME_NUMBER - (mazeBoard.getHeight())*TILE_SIZE + 5;
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

        for (int i = 0; i < mazeBoard.getColumns().size(); i++) {
            //Reserved space for start tile
            //if (i == 0) {
            //    y1 = 550 - TILE_SIZE;       //Start at the bottom of the frame
            //    y2 = y1;
            //} else {
                y1 = 550 - 2 * TILE_SIZE;
                y2 = y1;
            //}

            x1 = 225 + i * TILE_SIZE;

            for (int j = 0; j < mazeBoard.getColumns().get(i).size(); j++) {
                currTile = mazeBoard.getTile(i, j);

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

        g2d.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	// Dodgey and temporary
    	int dy = 0;
    	if (sPressed){
    		dy += 4;
    	}
    	if (wPressed){
    		dy -= 4;
    	}
    	int dx = 0;
    	if (dPressed){
    		dx += 4;
    	}
    	if (aPressed){
    		dx -= 4;
    	}
    	
    	// Dodgey stuff
    	int leftX = (x-SOME_NUMBER2)/TILE_SIZE;
    	int leftXNew = (x-SOME_NUMBER2 + dx)/TILE_SIZE;
    	int rightX = (x-SOME_NUMBER2 + SQUARE_SIZE)/TILE_SIZE;
    	int rightXNew = (x-SOME_NUMBER2 + SQUARE_SIZE+dx)/TILE_SIZE;
    	int topY = (SOME_NUMBER-y)/TILE_SIZE-1;
    	int topYNew = (SOME_NUMBER-y-dy)/TILE_SIZE-1;
    	int bottomY = (SOME_NUMBER-y-SQUARE_SIZE)/TILE_SIZE-1;
    	int bottomYNew = (SOME_NUMBER-y-SQUARE_SIZE-dy)/TILE_SIZE-1;
    	Tiles CurrentTile = mazeBoard.getTile(leftX, topY);
    	Tiles CurrentTile2 = mazeBoard.getTile(rightX, bottomY);
    	
    	if (dx > 0){
    		if ((rightX != rightXNew)){
    			if (topY != bottomY && mazeBoard.getTile(rightXNew, topY).isWall(Tiles.SOUTH)){
    				
    			}else if (!CurrentTile.isWall(Tiles.EAST) && !CurrentTile2.isWall(Tiles.EAST)){
    				x += dx;
    			}
    		}else{
    			x += dx;
    		}
    	}else if (dx < 0){
    		if (leftX != leftXNew){
    			if (topY != bottomY && mazeBoard.getTile(leftXNew, topY).isWall(Tiles.SOUTH)){
    				
    			}else if (!CurrentTile.isWall(Tiles.WEST) && !CurrentTile2.isWall(Tiles.WEST)){
    				x += dx;
    			}
    		}else{
    			x += dx;
    		}
    	}
    	
    	leftX = (x-SOME_NUMBER2)/TILE_SIZE;
    	rightX = (x-SOME_NUMBER2 + SQUARE_SIZE)/TILE_SIZE;
    	CurrentTile = mazeBoard.getTile(leftX, topY);
    	CurrentTile2 = mazeBoard.getTile(rightX, bottomY);
    	if (dy > 0){
    		if (bottomY != bottomYNew){
    			if (leftX != rightX && mazeBoard.getTile(rightX, bottomYNew).isWall(Tiles.EAST)){
    			}else if (!CurrentTile.isWall(Tiles.SOUTH) && !CurrentTile2.isWall(Tiles.SOUTH)){
        			y += dy;
        		}
    		}else{
    			y += dy;
    		}
    	}else if (dy < 0){
    		if (topY != topYNew){
    			if (leftX != rightX && mazeBoard.getTile(rightX, bottomYNew).isWall(Tiles.EAST)){
    			}else if (!CurrentTile.isWall(Tiles.NORTH) && !CurrentTile2.isWall(Tiles.NORTH)){
    				y+= dy;
        		}
    		}else{
    			y += dy;
    		}
    	}
    	x = Math.max(SOME_NUMBER2, x);
    	
        
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
