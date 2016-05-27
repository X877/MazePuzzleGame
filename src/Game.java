import java.util.Random;
import java.util.Stack;
/**
 * Game class moves player within board
 * When moving the player it considers how much time is left, how much 
 */
public class Game {
	public static double movementPerTick = 0.15;
	
	public static final int PLAYING = 0;
	public static final int WON = 1;
	public static final int PAUSED = 2;
	public static final int LOST = -1;
	private static final int INITIAL_BEER_VISION_BUFFER_TIME = 3000;
	private static final int INITIAL_BEER_STUMBLE_TIME = 3500;
	private static final int INITIAL_COFFEE_SPEEDUP_TIME = 3000;
	private static final int BOOK_TIME_BONUS = 8000;
	private static final int BEER_VISION_RANGE = 8;
	private static final int MAX_VISION_RANGE = 110;
	private static final int PLAYTIME = 600;
	private static final int PLAYING_ADDED_VISION_PER_TICK = 2;
	private static final int LOST_LOST_VISION_PER_TICK = 2;
	private static final int BEER_STUMBLE_SPEEDDOWN = 2;
	
	private int coffeeSpeedupTime;
	private int beerStumbleTime;
	private int beerVisionBufferTime;
	private int visionRange;
	private int time;
	private int state;
	private Board board;
	private Player player;
	private double playerSize;
	private Random rand;
	
	/**
	 * Initalises a new game
	 * @param board 		Board game is operating on
	 * @param playerSize 	Player size as a proportion of tile size to consider for movement operations
	 */
	public Game(Board board,double playerSize){
		rand = new Random();
		this.beerVisionBufferTime = 0;
		this.playerSize = playerSize;
		this.board = board;
		player = new Player();
		time = PLAYTIME*1000;
		state = PLAYING;
		visionRange = 0;
	}
		
	/**
	 * Main function for game, decreases times appropriately, updates vision, checks for power ups, moves player and checks for finishing of game
	 * @param keysPressed	Keys pressed to consider for movement
	 */
	public void tick(Stack<Character> keysPressed){
		
		decreaseTimes();
		updateVision();
		movePlayer(keysPressed);
		// If the player has finished, powerups don't need to be checked
		if(checkForFinish()){
			return;
		}
		checkForPowerups();
	}
	
	/**
	 * Updates the games internal timing values
	 */
	public void decreaseTimes(){
		// Decreases time left for game and checks for finish
		this.time = Math.max(0, this.time - GUI.tickTime);
		if (this.time == 0 && this.state == PLAYING){
			this.state = LOST;
		}
		beerVisionBufferTime = Math.max(0,beerVisionBufferTime-GUI.tickTime);
		beerStumbleTime = Math.max(0,beerStumbleTime-GUI.tickTime);
		coffeeSpeedupTime = Math.max(0,coffeeSpeedupTime-GUI.tickTime);
		
	}
	
	public void updateVision(){
		// Add vision to the player if the vision isn't buffering due to beers
		if (state == PLAYING){
			if (beerVisionBufferTime == 0){
				if (visionRange < MAX_VISION_RANGE) {
		            visionRange += PLAYING_ADDED_VISION_PER_TICK;
		        }
			}
		}
		
		// Decrease vision if game is lost
		if (state == LOST){
			if (visionRange > 0){
				visionRange -= LOST_LOST_VISION_PER_TICK;
			}
			visionRange = Math.max(0,visionRange);
		}
		
	}
	
	/**
	 * Moves character based on keys pressed
	 * @param keysPressed 	A stack of the keys currently held down
	 */
	public void movePlayer(Stack<Character> keysPressed){
		if (state != PLAYING){
			return;
		}
		
		double dx = 0;
		double dy = 0;
		
		// Determines to consider to move Y or X axis first
		boolean xFirst = true;
		boolean yFirst = false;
		if (!keysPressed.isEmpty()){
			if (keysPressed.peek() == 's' || keysPressed.peek() == 'w'){
				yFirst = true;
				xFirst = false;
			}else{
				xFirst = true;
			}
		}
		
		// Changes amount of movement depending on keys pressed
		if (keysPressed.contains('s')){
			dy -= movementPerTick;
		}
		if (keysPressed.contains('w')){
			dy += movementPerTick;
		}
		if (keysPressed.contains('a')){
			dx -= movementPerTick; 
		}
		if (keysPressed.contains('d')){
			dx += movementPerTick;
		}
		
		// Changes amount of movement based on powerups
		if (beerStumbleTime > 0){
			dx /= BEER_STUMBLE_SPEEDDOWN;
			dy /= BEER_STUMBLE_SPEEDDOWN;
			// Chance of random beer stumbling
			if (rand.nextInt(6) == 0){
				dy += rand.nextDouble()%(movementPerTick)*(double) beerStumbleTime/Game.INITIAL_BEER_STUMBLE_TIME;
				dx += rand.nextDouble()%(movementPerTick)*(double) beerStumbleTime/Game.INITIAL_BEER_STUMBLE_TIME;
			}
			// Chance of random beer opposite disorientation
			if (rand.nextInt(8) == 0){
				dy *= -1;
				dx *= -1;
			}
		}
		
		if (coffeeSpeedupTime > 0){
			dy *= 2;
			dx *= 2;
			// Chance of coffee twitching
			if (rand.nextInt(8) == 0){
				dy = rand.nextDouble()%(movementPerTick)/2;
				dx = rand.nextDouble()%(movementPerTick)/2;
			}
		}
		
		// Rounds the movement changes
		dx = roundDouble(dx);
		dy = roundDouble(dy);
		
		// Actually moves the player
		player.setDirection(Player.NONE);
		// If x first move x, and if x hasn't moved move y
		if (xFirst){
			boolean xMoved = moveX(dx);
			if (!xMoved){
				moveY(dy);
			}
		}
		// Opposite of above
		if (yFirst){
			boolean yMoved = moveY(dy);
			if (!yMoved){
				moveX(dx);
			}
		}
	}
	/**
	 * Check
	 * @return
	 */
	public boolean checkForFinish(){
 
		double x = player.getX();
		double y = player.getY();
		
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		int topY = (int) roundDouble(y);
		int bottomY  = (int) roundDouble(y + playerSize);
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		
		// If the player lies completely in a finish tile, i.e. top left and bottom right corners lie in finish tiles, consider the game finished		
		if (currentTile.isEndPoint() && currentTile2.isEndPoint()){
			state = WON;
			return true;
		}
		return false;
	}
	
	/**
	 * If player lies on a power up applies their effects and set their timeres
	 */
	public void checkForPowerups(){	
		double x = player.getX();
		double y = player.getY();
		
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		int topY = (int) roundDouble(y);
		int bottomY  = (int) roundDouble(y + playerSize);
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);

		// If  the top left and bottom right corners don't lie on empty tiles
		if (currentTile.getState()!= Tiles.EMPTY || currentTile2.getState() != Tiles.EMPTY) {
			// If either one lies on a beer, set the movement to be stumbly, and decrease the vision range
			if (currentTile.getState() == Tiles.BEER || currentTile2.getState() == Tiles.BEER){
				beerVisionBufferTime = Game.INITIAL_BEER_VISION_BUFFER_TIME;
				beerStumbleTime = Game.INITIAL_BEER_STUMBLE_TIME;
				if (visionRange > BEER_VISION_RANGE){
					visionRange = BEER_VISION_RANGE;
				}else{
					visionRange -= 5;
				}
				
				// If vision range is zero; the player has had a blackout, teleport the player randomly to a different square 
				if (visionRange <= 0){
					x += (rand.nextInt(10)-5);
					y += (rand.nextInt(10)-5);
					x = Math.max(0,x);
					y = Math.max(0,x);
					x = Math.min(x,board.getWidth()-1);
					y = Math.min(y,board.getHeight()-1);
					player.setXY(x, y);
				}
			}
			// If the player is on a book, apply the book bonus
			if (currentTile.getState() == Tiles.BOOK || currentTile2.getState() == Tiles.BOOK){
				time += BOOK_TIME_BONUS;
			}
			
			// If the player is on a coffee, apply the coffee speedup bonus
			if (currentTile.getState() == Tiles.COFFEE || currentTile2.getState() == Tiles.COFFEE){
				coffeeSpeedupTime = Game.INITIAL_COFFEE_SPEEDUP_TIME;
			}
			// Clear both tiles of their state
			currentTile.setState(Tiles.EMPTY);
			currentTile2.setState(Tiles.EMPTY);
		}
	}
	
	/**
	 * Moves the player in the x plane
	 * @param dx Amount of movement to test for, should be under 1
	 * @return If the player has successfully moved or not
	 */
	public boolean moveX(double dx){
		double x = player.getX();
		double y = player.getY();
		
		// Find integer flattened coordinate of the left and right coordinates and their new positions
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		int leftXNew = (int) roundDouble(x+dx);
		int rightXNew = (int) roundDouble(x+playerSize+dx);
		int topY = (int) roundDouble(y);
		int bottomY  = (int) roundDouble(y + playerSize);
		
		// If the player is next to the right wall, don't bother moving to avoid out of bounds checking
		if (rightXNew >= board.getWidth()){
			return false;
		}
		
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		
		double xBefore = x;
		
		// If the player is moving right
		if (dx > 0){
			// If the player is moving to a new tile
			if (rightX != rightXNew){
				// If the player will be spiked by a horizontal wall, don't move
				if (topY != bottomY && board.getTile(rightXNew, topY).isWall(Tiles.NORTH)){
				// If the player doesn't have any wall in the west
				}else if (!currentTile.isWall(Tiles.EAST) && !currentTile2.isWall(Tiles.EAST)){
					x += dx;
				}
			// If the player isn't, movement is automatically safe
			}else{
				x += dx;
			}
		// If the player is moving left			
		}else if (dx < 0){
			// If the player is moving to a new tile
			if (leftX != leftXNew){
				// If the player will be spiked by a horizontal wall, don't move 
				if (topY != bottomY && board.getTile(leftXNew, topY).isWall(Tiles.NORTH)){
				
				// If the player doesn't have any wall in the west
				}else if (!currentTile.isWall(Tiles.WEST) && !currentTile2.isWall(Tiles.WEST)){
					x += dx;
				}
			// If the player isn't, movement is automatically safe
			}else{
				x += dx;
			}
		}
		// Rounds X and makes sure it is in bounds
		x = roundDouble(x);
		x = Math.max(x,0);
		
		// If x hasn't changed, just return false without updating coords and player direction
		if (xBefore == x){
			return false;
		}
		
		// If player moved right show so
		if (dx > 0){
			player.setDirection(Player.RIGHT);
		}else if (dx < 0){
		// If player moved left show so
			player.setDirection(Player.LEFT);
		}
		// Update coordinates
		player.setXY(x,y);
		return true;
	}
	
	/**
	 * Moves the player in the y plane
	 * @param dy Amount of movement to test for, should be under 1
	 * @return If the player has successfully moved or not
	 */
	public boolean moveY(double dy){
		// Works the same way as X, except for y
		// Thing to look out for is Y is inverted (i.e. smaller Y = moving down)
		double x = player.getX();
		double y = player.getY();
		
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		
		int topY = (int) roundDouble(y);
		int topYNew = (int) roundDouble(y+dy);
		int bottomY  = (int) roundDouble(y + playerSize);
		int bottomYNew = (int) roundDouble(y+playerSize+dy);
		if (bottomYNew >= board.getHeight()){
			return false;
		}
		
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		
		double yBefore = y;
		if (dy > 0){
			if (bottomY != bottomYNew){
				if (leftX != rightX && board.getTile(rightX, bottomYNew).isWall(Tiles.WEST)){
					return false;
				}else if (!currentTile.isWall(Tiles.NORTH) && !currentTile2.isWall(Tiles.NORTH)){
					y += dy;
				}
			}else{
				y += dy;
			}
		}else if (dy < 0){
			if (topY != topYNew){
				if (leftX != rightX && board.getTile(rightX, topYNew).isWall(Tiles.WEST)){
					return false;
				}else if (!currentTile.isWall(Tiles.SOUTH) && !currentTile2.isWall(Tiles.SOUTH)){
					y += dy;
				}
			}else{
				y += dy;
			}
		}
		
		y = roundDouble(y);
		y = Math.max(y,0);
		
		if (yBefore == y){
			return false;
		}
		
		if (dy > 0){
			player.setDirection(Player.UP);
		}else if (dy < 0){
			player.setDirection(Player.DOWN);
		}
		
		player.setXY(x,y);
		return true;
	}
	
	double roundDouble(double x){
		return (double) Math.round(x * 100)/100;
	}
	

	public int getVisionRange(){
		return visionRange;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Board getBoard(){
		return board;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getTime(){
		return time;
	}
	
	public double getPlayerSize(){
		return playerSize;
	}
	public void setVisionRange(int range){
		visionRange = range;
	}
}
