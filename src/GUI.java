
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
    private int diameter;
	public static final int tickTime = 10;
	private static final int ticksPerImageRotation = 8;
	private int subImageRotation = 0;
	private int imageRotation = 0;
	private Image[] leftPlayerImages;
	private Image[] rightPlayerImages;
	private Image[] upPlayerImages;
	private Image[] downPlayerImages;
	private Image[] stayingPlayerImages;
	private Image img;
	
	private Stack<Character> keysPressed;
	private Game game;
    private Timer actionTimer;
    

    private JLabel timerLbl;
    private JButton btnBackToMenu;
    

    public GUI(Board mazeBoard, int tileSize, int posFromBottom, int posFromLeft) {

        this.tileSize = tileSize;
        this.posFromBottom = posFromBottom;
        this.posFromLeft = posFromLeft;
        this.diameter=0;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.actionTimer = new Timer(tickTime, this);
        this.actionTimer.start();
        
        this.upPlayerImages = new Image[2];
        this.downPlayerImages = new Image[2];
        this.leftPlayerImages = new Image[2];
        this.rightPlayerImages = new Image[2];
        this.stayingPlayerImages = new Image[1];
        
        //this.img = new ImageIcon(this.getClass().getResource("/wallpaper3.png")).getImage();
        
        
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
        addTimerLbl();
    }

    /**
     *
     * @param g
     */
    private void drawBoard(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        Tiles currTile;

        int x1 = 0;
        int y1 = 0;

        for (int i = 0; i < game.getBoard().getColumns().size(); i++) {
            y1 = (int) (posFromBottom - 2 * tileSize);
            x1 = (int) (posFromLeft + (i * tileSize));

            for (int j = 0; j < game.getBoard().getColumns().get(i).size(); j++) {
                String tileToDraw = "";
                currTile = game.getBoard().getTile(i, j);

                //All walls check (Start from North)
                if (currTile.isWall(Tiles.NORTH)) {
                    tileToDraw = "Up";
                    if (currTile.isWall(Tiles.EAST)) {
                        tileToDraw = "UpRight";
                        if (currTile.isWall(Tiles.SOUTH)) {
                            tileToDraw = "UpRightDown";
                            if (currTile.isWall(Tiles.WEST)) {
                                tileToDraw = "All";
                            }
                        //North, East and West
                        } else if (currTile.isWall(Tiles.WEST)) {
                            tileToDraw = "UpRightLeft";
                        }
                    //North, South and West
                    } else if (currTile.isWall(Tiles.SOUTH)) {
                        tileToDraw = "UpDown";
                        if (currTile.isWall(Tiles.WEST)) {
                            tileToDraw = "UpDownLeft";
                        }
                    //North and West
                    } else if (currTile.isWall(Tiles.WEST)) {
                        tileToDraw = "UpLeft";
                    }

                //East wall
                } else if (currTile.isWall(Tiles.EAST)) {
                    tileToDraw = "Right";
                    //East and South
                    if (currTile.isWall(Tiles.SOUTH)) {
                        tileToDraw = "RightDown";
                        //East, South and West
                        if (currTile.isWall(Tiles.WEST)) {
                            tileToDraw  = "RightDownLeft";
                        }
                    //East and West
                    } else if (currTile.isWall(Tiles.WEST)) {
                        tileToDraw = "RightLeft";
                    }

                }

                //South wall
                else if (currTile.isWall(Tiles.SOUTH)) {
                    tileToDraw = "Down";
                    //South and West
                    if (currTile.isWall(Tiles.WEST)) {
                        tileToDraw = "DownLeft";
                    }
                }

                //West wall
                else if (currTile.isWall(Tiles.WEST)) {
                    tileToDraw = "Left";
                }

                //Decide which tiles to draw
                switch (tileToDraw) {
                    case "All":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_All.png")).getImage());
                        break;
                    case "UpRightDown":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_UpDownRight.png")).getImage());
                        break;
                    case "UpRightLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_LeftRightUp.png")).getImage());
                        break;
                    case "UpRight":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_RightUp.png")).getImage());
                        break;
                    case "Up":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_Up.png")).getImage());
                        break;
                    case "UpDownLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_UpDownLeft.png")).getImage());
                        break;
                    case "UpDown":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_UpDown.png")).getImage());
                        break;
                    case "UpLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_LeftUp.png")).getImage());
                        break;
                    case "RightDownLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_LeftRightDown.png")).getImage());
                        break;
                    case "RightDown":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_RightDown.png")).getImage());
                        break;
                    case "RightLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_LeftRight.png")).getImage());
                        break;
                    case "Right":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_Right.png")).getImage());
                        break;
                    case "DownLeft":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_LeftDown.png")).getImage());
                        break;
                    case "Down":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_Down.png")).getImage());
                        break;
                    case "Left":
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_Left.png")).getImage());
                        break;
                    default:
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/Tile_None.png")).getImage());
                        break;
                }

                //If it's not start/end point, draw the tile
                if (!currTile.isStartPoint() && !currTile.isEndPoint()) {
                    g2d.drawImage(currTile.getTile(), x1, y1, null);
                }
                //Change position upwards for next iteration
                y1 -= tileSize;
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
        
        
        if (game.getState() == game.LOST || game.getState() == game.WON) { 	
    		diameter -= 5;     
        } else if (game.getState() == game.PLAYING) {
        	if (diameter != 4000) {
            	diameter += 15;
        	}
        }
        drawFog(xCentre,yCentre,diameter,g2d);

    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tickTime();
        g.drawImage(img, 0, 0, this);
        drawBoard(g);
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
    
    public void addTimerLbl(){
		this.timerLbl = new JLabel();
		this.timerLbl.setText(Integer.toString(game.getTime()));
		
		this.timerLbl.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		this.timerLbl.setForeground(Color.white);
		this.timerLbl.setBorder(BorderFactory.createLineBorder(Color.white));
		this.timerLbl.setVisible(true);
		
		this.timerLbl.setBounds(583, 100, 200, 40);
		this.add(timerLbl);
    }

    public void tickTime() {
        this.timerLbl.setText(Integer.toString(game.getTime()));
    }
}
