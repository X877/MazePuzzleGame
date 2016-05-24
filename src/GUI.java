import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.Stack;


/**
 * Drawing boards with 2d graphic
 */

public class GUI extends JPanel implements KeyListener, ActionListener{
	
    private int tileSize;
    private int posFromBottom;
    private int posFromLeft;
	public static final int tickTime = 10;
	private static final int ticksPerImageRotation = 5;
	private int subImageRotation = 0;
	private int imageRotation = 0;
	private Image[] leftPlayerImages;
	private Image[] rightPlayerImages;
	private Image[] upPlayerImages;
	private Image[] downPlayerImages;
	private Image[] stayingPlayerImages;
	
	private Stack<Character> keysPressed;
	private Game game;
    private Timer actionTimer;
    

    public GUI(Board mazeBoard, int tileSize, int posFromBottom, int posFromLeft) {
	
        this.tileSize = tileSize;
        
        this.posFromBottom = posFromBottom;
        this.posFromLeft = posFromLeft;

        this.addKeyListener(this);
        this.setFocusable(true);
        this.actionTimer = new Timer(tickTime, this);
        this.actionTimer.start();
        
        this.upPlayerImages = new Image[2];
        this.downPlayerImages = new Image[2];
        this.leftPlayerImages = new Image[2];
        this.rightPlayerImages = new Image[2];
        this.stayingPlayerImages = new Image[1];
        
        
        this.game = new Game(mazeBoard,(double)16/this.tileSize);
        
        upPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Backwards_1.png")).getImage();
        upPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Backwards_2.png")).getImage();
        
        downPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Forwards_1.png")).getImage();
        downPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Forwards_2.png")).getImage();
        
        rightPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Right_1.png")).getImage();
        rightPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Right_2.png")).getImage();
        
        leftPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Left_1.png")).getImage();
        leftPlayerImages[1] = new ImageIcon(this.getClass().getResource("/Player_Left_2.png")).getImage();
        
        stayingPlayerImages[0] = new ImageIcon(this.getClass().getResource("/Player_Still.png")).getImage();
        keysPressed = new Stack<Character>();
        
        this.subImageRotation = 0;
        this.imageRotation = 0;
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
        int playerSize = (int) (tileSize*game.getPlayerSize()+1);
        int playerDisplayX =(int)(posFromLeft+player.getX()*tileSize)+1;
        int playerDisplayY = (int)(posFromBottom-player.getY()*tileSize-tileSize+2-playerSize);
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
    
        
        
        Image img[] = null; 
        //g2d.fillRect(playerDisplayX, playerDisplayY, playerSize, playerSize);
        if (player.getDirection() == Player.UP){
        	img  = upPlayerImages;
        }else if (player.getDirection() == Player.DOWN){
        	img  = downPlayerImages;
        }else if (player.getDirection() == Player.LEFT){
        	img  = leftPlayerImages;
        }else if (player.getDirection() == Player.RIGHT){
        	img  = rightPlayerImages;
        }else if (player.getDirection() == Player.NONE){
        	img  = stayingPlayerImages;
        }
        
        subImageRotation++;
        
        if (subImageRotation == GUI.ticksPerImageRotation){
        	this.imageRotation = (this.imageRotation+1)%img.length;
        	subImageRotation = 0;
        }
        
        if (img.length == 1){
        	imageRotation = 0;
        }
        //System.out.println(imageRotation +" " + player.getDirection());
        
        //System.out.println(player.getDirection());


        
        //System.out.println("CurrentImage" +  this.image + img);
        g2d.drawImage(img[imageRotation], playerDisplayX, playerDisplayY, null);
        
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
        Rectangle rect = new Rectangle(posFromLeft, posFromBottom - tileSize*game.getBoard().getHeight()-tileSize,
        		game.getBoard().getWidth()*tileSize, tileSize*game.getBoard().getHeight());
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
