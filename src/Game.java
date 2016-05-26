import java.util.Random;
import java.util.Stack;

public class Game {
	// MOVEMENT PER TICK
	public static double MovementPerTick = 0.15;
	
	public static final int PLAYING = 0;
	public static final int WON = 1;
	public static final int PAUSED = 2;
	public static final int LOST = -1;
	private static final int INITIAL_VISION_BUFFER_TIME = 2500;
	private static final int INITIAL_BEER_STUMBLE_TIME = 3500;
	private static final int INITIAL_COFFEE_SPEEDUP_TIME = 3000;
	private static final int BOOK_TIME_BONUS = 8000;
	private static final int BEER_VISION_RANGE = 8;
	private static final int MAX_VISION_RANGE = 100;
	
	private int coffeeSpeedupTime;
	private int beerStumbleTime;
	private int visionBufferTime;
	private int visionRange;
	private int time;
	private int state;
	private Board board;
	private Player player;
	private double playerSize;
	
	public Game(Board board,double playerSize){
		this.visionBufferTime = 0;
		this.playerSize = playerSize;
		this.board = board;
		player = new Player();
		time = 100*100;
		state = PLAYING;
		visionRange = 0;
	}
	
	public int getVisionRange(){
		return visionRange;
	}
	
	public void setVisionRange(int visionRange){
		this.visionRange = visionRange; 
	}
	
	public void addVisionRange(int range){
		this.visionRange += range; 
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
		//System.out.println(playerSize);
		return playerSize;
	}
	
	public void tick(){
		this.time -= GUI.tickTime;
		if (this.time < 0 && this.state == PLAYING){
			this.state = LOST;
		}
		if (visionBufferTime > 0){
			visionBufferTime -= GUI.tickTime;
		}
		if (visionBufferTime < 0){
			visionBufferTime = 0;
		}
		
		if (beerStumbleTime > 0){
			beerStumbleTime -= GUI.tickTime;
		}
		if (beerStumbleTime  < 0){
			beerStumbleTime  = 0;
		}
		
		if (coffeeSpeedupTime > 0){
			coffeeSpeedupTime-= GUI.tickTime;
		}
		if (coffeeSpeedupTime < 0){
			coffeeSpeedupTime = 0;
		}
		if (coffeeSpeedupTime > 0){
			MovementPerTick = 0.3;
		}else{
			MovementPerTick = 0.15;
		}
		
		
		if (state == PLAYING){
			if (visionBufferTime == 0){
				if (visionRange < MAX_VISION_RANGE) {
		            visionRange += 1;
		        }
			}
		}
		
		if (state == LOST){
			if (visionRange > 0){
				visionRange -= 2;
			}
			visionRange = Math.max(0,visionRange);
		}
		
		//System.out.println(time);
	}
	
	public void movePlayer(Stack<Character> keysPressed){
		if (state != PLAYING){
			return;
		}
		
		
		double dx = 0;
		double dy = 0;
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
		
		if (keysPressed.contains('s')){
			dy -= MovementPerTick;
		}
		if (keysPressed.contains('w')){
			dy += MovementPerTick;
		}
		if (keysPressed.contains('a')){
			dx -= MovementPerTick; 
		}
		if (keysPressed.contains('d')){
			dx += MovementPerTick;
		}
		Random rand = new Random();
		if (beerStumbleTime > 0){
			dy += rand.nextDouble()%(MovementPerTick/0.6)*(double) beerStumbleTime/this.INITIAL_BEER_STUMBLE_TIME;
			dx += rand.nextDouble()%(MovementPerTick/0.6)*(double) beerStumbleTime/this.INITIAL_BEER_STUMBLE_TIME;
		}
		
		if (coffeeSpeedupTime > 0){
			if (rand.nextInt(4) == 0){
				dy = rand.nextDouble()%(MovementPerTick);
				dx = rand.nextDouble()%(MovementPerTick);
			}
		}
		
		player.setDirection(Player.NONE);
		//dx = roundDouble(dx);
		//dy = roundDouble(dy);
		if (xFirst){
			boolean xMoved = moveX(dx);
			if (!xMoved){
				moveY(dy);
			}
		}
		
		if (yFirst){
			boolean yMoved = moveY(dy);
			if (!yMoved){
				moveX(dx);
			}
		}
		
		double x = player.getX();
		double y = player.getY();
		
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		int topY = (int) roundDouble(y);
		int bottomY  = (int) roundDouble(y + playerSize);
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		
		if (currentTile.isEndPoint() && currentTile2.isEndPoint()){
			state = WON;
			return;
		}

		if (currentTile.getState() != Tiles.EMPTY) {
			if (currentTile.getState() == Tiles.BEER){
				visionBufferTime = Game.INITIAL_VISION_BUFFER_TIME;
				beerStumbleTime = Game.INITIAL_BEER_STUMBLE_TIME;
				if (visionRange > BEER_VISION_RANGE){
					visionRange = BEER_VISION_RANGE;
				}else{
					visionRange -= 5;
				}
			}
			if (currentTile.getState() == Tiles.BOOK){
				time += BOOK_TIME_BONUS;
			}
			if (currentTile.getState() == Tiles.COFFEE){
				coffeeSpeedupTime = Game.INITIAL_COFFEE_SPEEDUP_TIME;
			}
			currentTile.setState(Tiles.EMPTY);
		}
		
		if (currentTile2.getState() != Tiles.EMPTY) {
			if (currentTile2.getState() == Tiles.BEER){
				beerStumbleTime = Game.INITIAL_BEER_STUMBLE_TIME;
				visionBufferTime = Game.INITIAL_VISION_BUFFER_TIME;
				if (visionRange > BEER_VISION_RANGE){
					visionRange = BEER_VISION_RANGE;
				}else{
					visionRange -= 5;
				}
			}
			if (currentTile2.getState() == Tiles.BOOK){
				time += BOOK_TIME_BONUS;
			}
			if (currentTile2.getState() == Tiles.COFFEE){
				coffeeSpeedupTime = Game.INITIAL_COFFEE_SPEEDUP_TIME;
			}
			currentTile2.setState(Tiles.EMPTY);
		}


		//System.out.println(x
		//		+" "+ y);
		// Many code
	}
	
	public boolean moveX(double dx){
		double x = player.getX();
		double y = player.getY();
		
		int rightX = (int) roundDouble(x + playerSize);
		int leftX = (int) roundDouble(x);
		int leftXNew = (int) roundDouble(x+dx);
		int rightXNew = (int) roundDouble(x+playerSize+dx);		
		if (rightXNew >= board.getWidth()){
			return false;
		}
		
		int topY = (int) roundDouble(y);
		int bottomY  = (int) roundDouble(y + playerSize);
		
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		
		double xBefore = x;
		if (dx > 0){
			if (rightX != rightXNew){
				if (topY != bottomY && board.getTile(rightXNew, topY).isWall(Tiles.NORTH)){
				}else if (!currentTile.isWall(Tiles.EAST) && !currentTile2.isWall(Tiles.EAST)){
					x += dx;
				}
			}else{
				x += dx;
			}
			
		}else if (dx < 0){
			if (leftX != leftXNew){
				if (topY != bottomY && board.getTile(leftXNew, topY).isWall(Tiles.NORTH)){
					
				}else if (!currentTile.isWall(Tiles.WEST) && !currentTile2.isWall(Tiles.WEST)){
					x += dx;
				}
			}else{
				x += dx;
			}
		}
		
		x = roundDouble(x);
		y = roundDouble(y);
		x = Math.max(x,0);
		y = Math.max(y,0);
		
		if (xBefore == x){
			return false;
		}
		
		if (dx > 0){
			player.setDirection(Player.RIGHT);
		}else if (dx < 0){
			player.setDirection(Player.LEFT);
		}
		player.setXY(x,y);
		return true;
	}
	
	public boolean moveY(double dy){
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
		
		x = roundDouble(x);
		y = roundDouble(y);
		x = Math.max(x,0);
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
	
}
