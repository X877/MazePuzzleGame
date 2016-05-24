import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Stack;


/**
 * Drawing boards with 2d graphic
 */

public class GUI extends JPanel implements KeyListener, ActionListener{
	private static int TILE_SIZE = 25;
	private static int MAZE_BOTTOM = 550;
	private static int MAZE_LEFT = 215;
	public static final int tickTime = 20;
	private int imageRotation = 0;
	private Image[] leftPlayerImages;
	private Image[] rightPlayerImages;
	private Image[] upPlayerImages;
	private Image[] downPlayerImages;
	private Image[] stayingPlayerImages;
	
	private Stack<Character> keysPressed;
	private Game game;
    private Timer actionTimer;
    
    public GUI(Board mazeBoard) {
        this.game = new Game(mazeBoard,(double)16/25);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.actionTimer = new Timer(tickTime, this);
        this.actionTimer.start();
        
        this.upPlayerImages = new Image[2];
        this.downPlayerImages = new Image[2];
        this.leftPlayerImages = new Image[2];
        this.rightPlayerImages = new Image[2];
        this.stayingPlayerImages = new Image[2];
        
        upPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Backwards_1.png")).getImage();
        upPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Backwards_2.png")).getImage();
        
        downPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Forwards_1.png")).getImage();
        downPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Forwards_2.png")).getImage();
        
        rightPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Right_1.png")).getImage();
        rightPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Right_2.png")).getImage();
        
        leftPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Left_1.png")).getImage();
        leftPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Left_2.png")).getImage();
        
        stayingPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Still.png")).getImage();
        stayingPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Still.png")).getImage();

        
        keysPressed = new Stack<Character>();
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
        
        int playerSize = (int) (TILE_SIZE*game.getPlayerSize()+1);
        int playerDisplayX =(int)(MAZE_LEFT+player.getX()*TILE_SIZE)+1;
        int playerDisplayY = (int)(MAZE_BOTTOM-player.getY()*TILE_SIZE-TILE_SIZE+1-playerSize);
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
    
        if (game.getTime() % 200 == 0){
        	this.imageRotation = (this.imageRotation+1)%2;
        }
        
        Image img = null; 
        //g2d.fillRect(playerDisplayX, playerDisplayY, playerSize, playerSize);
        if (player.getDirection() == player.UP){
        	img  = upPlayerImages[imageRotation];
        	System.out.println("up");
        }else if (player.getDirection() == player.DOWN){
        	img  = downPlayerImages[imageRotation];
        	System.out.println("down");
        }else if (player.getDirection() == player.LEFT){
        	img  = leftPlayerImages[imageRotation];
        }else if (player.getDirection() == player.RIGHT){
        	img  = rightPlayerImages[imageRotation];
        }else if (player.getDirection() == player.NONE){
        	img  = stayingPlayerImages[imageRotation];
        }else{
        	img  = stayingPlayerImages[0];
        }
        System.out.println(player.getDirection());


        
        //System.out.println("CurrentImage" +  this.image + img);
        g2d.drawImage(img, playerDisplayX, playerDisplayY, null);
        
        int xCentre = playerDisplayX + playerSize/2;
        int yCentre = playerDisplayY + playerSize/2;
        int diameter = 500;
        
        drawFog(xCentre,yCentre,diameter,g2d);
        
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	this.requestFocus();
    	game.tick();
    	game.movePlayer(keysPressed);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if (!keysPressed.contains(e.getKeyChar())){
    		//System.out.println(ADDING" + keysPressed);
    		keysPressed.add(e.getKeyChar());
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	keysPressed.remove((Object) e.getKeyChar());
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    private void drawFog(int xCentre, int yCentre, int diameter, Graphics2D g2d){
    	g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
        	      RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        Rectangle rect = new Rectangle(MAZE_LEFT, MAZE_BOTTOM - TILE_SIZE*game.getBoard().getHeight()-TILE_SIZE, game.getBoard().getWidth()*TILE_SIZE, TILE_SIZE*game.getBoard().getHeight());
        Ellipse2D spot = new Ellipse2D.Float(
                (float) xCentre - (diameter / 2f),
                (float) yCentre - (diameter / 2f),
                (float) diameter,
                (float) diameter);
        Area area = new Area(rect);
        area.subtract(new Area(spot));
        g2d.fill(area);
    }
    
}
