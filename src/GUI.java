
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
    private int counter;
    public static final int tickTime = 10;
	private static final int ticksPerImageRotation = 8;
	private int subImageRotation = 0;
	private int imageRotation = 0;
    private String level;
    private String count;
	private Image[] leftPlayerImages;
	private Image[] rightPlayerImages;
	private Image[] upPlayerImages;
	private Image[] downPlayerImages;
	private Image[] stayingPlayerImages;
	private Image img;
	
	private Stack<Character> keysPressed;
	private Game game;
    private Timer actionTimer;
    private JFrame exitMainMenu;

    private JLabel timerLbl;
    private JButton btnBackToMenu;
    private JLabel fadeLoseLbl;
    private Timer fadeTimer;
    private JTextArea levelScore;
    private JTextArea totalScore;
    private int endTime;
    
    private JFrame frame;
    

    public GUI(Board mazeBoard, int tileSize, int posFromBottom, int posFromLeft, int difficulty, JFrame frame){

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
        this.endTime = 0;
        addBackToMenuBtn(frame);
        this.frame = frame;

        //this.img = new ImageIcon(this.getClass().getResource("/images/wallpaper3.png")).getImage();
        
        this.game = new Game(mazeBoard,(double)16/this.tileSize);

        keysPressed = new Stack<Character>();
        
        this.subImageRotation = 0;
        this.imageRotation = 0;
        addTimerLbl();

        if (difficulty == 1) {
            this.level = "Easy";
        } else if (difficulty == 2) {
            this.level = "Medium";
        } else if (difficulty == 3) {
            this.level = "Hard";
        } else if (difficulty == 4) {
            this.level = "Hopes&Dreams";
        }

        this.count = "";
    }

    /**
     * Method to draw the maze board
     * @param g
     */
    private void drawBoard(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Tiles currTile;

        int x1 = 0;
        int y1 = 0;
        int powerUpX = 0;
        int powerUpY = 0;

        for (int i = 0; i < game.getBoard().getColumns().size(); i++) {
            x1 = posFromLeft + (i * tileSize);
            y1 = posFromBottom - (2 * tileSize);

            powerUpX = (int) (x1 + (0.25 * tileSize));
            powerUpY = (int) (y1 + (0.25 * tileSize));


            for (int j = 0; j < game.getBoard().getColumns().get(i).size(); j++) {
                currTile = game.getBoard().getTile(i, j);
                String tileToDraw = wallChecks(currTile);

                //Decide which tiles to draw
                if (tileToDraw.equals("UpRightDownLeft")) {
                    currTile.setTile(new ImageIcon(this.getClass().getResource("/images/" + level + "/Tiles/Tile_All.png")).getImage());
                } else if (tileToDraw.equals("")) {
                    currTile.setTile(new ImageIcon(this.getClass().getResource("/images/" + level + "/Tiles/Tile_None.png")).getImage());
                } else {
                    currTile.setTile(new ImageIcon(this.getClass().getResource("/images/" + level + "/Tiles/Tile_" + tileToDraw + ".png")).getImage());
                }

                //If it's not start/end point, draw the tile
                if (!currTile.isStartPoint() && !currTile.isEndPoint()) {
                    g2d.drawImage(currTile.getTile(), x1, y1, null);

                    if (currTile.getState() != 0) {
                        if (currTile.getState() == 1) {
                            tileToDraw = "Beer";
                        } else if (currTile.getState() == 2) {
                            tileToDraw = "Coffee";
                        } else {
                            tileToDraw = "Book";
                        }
                        currTile.setTile(new ImageIcon(this.getClass().getResource("/images/" + level + "/PickUps/" + tileToDraw + ".png")).getImage());
                        g2d.drawImage(currTile.getTile(), powerUpX, powerUpY - counter /6, null);
                    }
                }
                //Change position upwards for next iteration
                y1 -= tileSize;
                powerUpY -= tileSize;
            }
        }
        if (counter == 18){
            count = "down";
        } else if (counter == 0) {
            count = "up";
        }
        if (count.equals("up")) {
            counter++;
        } else if (count.equals("down")){
            counter--;
        }
    }

    /**
     * Method to draw the player
     * @param g
     */
    public void drawPlayer(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        upPlayerImages[0] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Backwards_1.png")).getImage();
        upPlayerImages[1] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Backwards_2.png")).getImage();

        downPlayerImages[0] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Forwards_1.png")).getImage();
        downPlayerImages[1] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Forwards_2.png")).getImage();

        rightPlayerImages[0] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Right_1.png")).getImage();
        rightPlayerImages[1] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Right_2.png")).getImage();

        leftPlayerImages[0] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Left_1.png")).getImage();
        leftPlayerImages[1] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Left_2.png")).getImage();

        stayingPlayerImages[0] = new ImageIcon(this.getClass().getResource("/images/" + level + "/Player/Player_Still.png")).getImage();

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

        g2d.drawImage(img[imageRotation], playerDisplayX, playerDisplayY, null);
    }

    /**
     * Method to draw fog around the player
     * @param g
     */
    public void drawBoardFog(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        Player player = game.getPlayer();
        int playerSize = (int) (tileSize*game.getPlayerSize()+1);
        int playerDisplayX =(int)(posFromLeft+player.getX()*tileSize)+1;
        int playerDisplayY = (int)(posFromBottom-player.getY()*tileSize-tileSize+2-playerSize);

        int xCentre = playerDisplayX + playerSize/2;
        int yCentre = playerDisplayY + playerSize/2;

        if (game.getState() == game.LOST) {
            //Change the timer text
            this.timerLbl.setText("TIME IS UP!!!");
            //Store the end time once only
            if (Math.abs(game.getTime()) > 0 && this.endTime == 0){
                this.endTime = Math.abs(game.getTime());
            }
            System.out.println(this.endTime + " " + Math.abs(game.getTime()));
            //Game waits 450ms for the fog to cover the screen before going to the lose screen
            if(Math.abs(game.getTime())- this.endTime >= 450){
            	g.clearRect(0, 0, 9999, 9999);
                actionTimer.stop();
                JButton exit = new JButton();
                JLabel loseLabel = new JLabel();
                Image loseImage  = new ImageIcon(this.getClass().getResource("/images/wallpaper3.png")).getImage();
                exit = new JButton("Return To Main Menu");
                exit.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
                exit.setBounds(583, 350, 300, 150);
                loseLabel.setIcon(new ImageIcon(loseImage));
                loseLabel.add(exit);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(loseLabel);
                frame.revalidate();
                
                //Adds a button to go back to menu
                exit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        MenuLabel menu = new MenuLabel(frame);
                        menu.setVisible(true);
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(menu);
                        frame.revalidate();
                    }
                });

    		    /*
        	    Font end = new Font("Copperplate Gothic Light", Font.BOLD, 30);
        	    g.setFont(end);
        	    g.setColor(Color.WHITE);
        	    g.drawString("Time has run out. You have to live among other hobos", 236, 200);
        	   */
                //actionTimer.stop();
            }

        } else if (game.getState() == game.WON){


        } else if (game.getState() == game.PLAYING) {
            
        }
        
        int diameter = game.getVisionRange() * tileSize;
        drawFog(xCentre, yCentre, diameter, g2d);


    }

    /**
     * Method to checks where the walls are in a tile
     * @param currTile current tile
     * @return a String of where the walls at
     */
    public String wallChecks(Tiles currTile) {
        String tileToDraw = "";

        //All walls check (Start from North)
        if (currTile.isWall(Tiles.NORTH)) {
            tileToDraw += "Up";
        }
        if (currTile.isWall(Tiles.EAST)) {
            tileToDraw += "Right";
        }
        if (currTile.isWall(Tiles.SOUTH)) {
            tileToDraw += "Down";
        }
        if (currTile.isWall(Tiles.WEST)) {
            tileToDraw += "Left";
        }

        return tileToDraw;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tickTime();
        g.drawImage(img, 0, 0, this);
        if (game.getState() == game.PAUSED){
        	drawPlayer(g);
        	drawBoardFog(g);
        }else{
	        drawBoard(g);
	        drawPlayer(g);
	        drawBoardFog(g);
        }
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
    
    public void addBackToMenuBtn(final JFrame frame){
		btnBackToMenu = new JButton("Main Menu");
		btnBackToMenu.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnBackToMenu.setBounds(583, 550, 200, 50);
		add(btnBackToMenu);
		
        //If the button is pressed open up the window option and pause the game
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTimer.stop();
				int oldState = game.getState();
				game.setState(Game.PAUSED);
			    String message = "Are you sure you wish to exit to main menu?";
			    int answer = JOptionPane.showConfirmDialog(exitMainMenu, message, message, JOptionPane.YES_NO_OPTION);
			    if(answer == JOptionPane.YES_NO_OPTION) {
			        MenuLabel menu = new MenuLabel(frame);
					menu.setVisible(true);	
					frame.getContentPane().removeAll();
					frame.getContentPane().add(menu);
				    frame.revalidate();
			    } else if (answer == JOptionPane.NO_OPTION || answer == JOptionPane.CANCEL_OPTION){
			    	game.setState(oldState);
			    	//continue the game
			        actionTimer.start();
			        exitMainMenu.getContentPane().removeAll();
					exitMainMenu.revalidate();	
			    } 
			}
		});
	}
    
    public void goBackToMenuPanel(final JFrame frame){
    	JLabel endGame = new JLabel();
        Image img2;
        endGame.setText("");
		img2 = new ImageIcon(this.getClass().getResource("/images/wallpaper3.png")).getImage();
		endGame.setIcon(new ImageIcon(img2));
		
		endGame.setVisible(true);
		this.setVisible(true);
		this.revalidate();
		frame.getContentPane().removeAll();
		frame.getContentPane().add(endGame);
	    frame.revalidate();
    }
    
    public void addLevelHighScore(){
    	
    }
    public void addTotalHighScore(){
    	
    }
}

