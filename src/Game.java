import java.util.Stack;

public class Game {
	// MOVEMENT PER TICK
	public static double MovementPerTick = 0.15;
	
	public static final int PLAYING = 0;
	public static final int WON = 1;
	public static final int PAUSED = 2;
	public static final int LOST = -1;
	private int visionRange;
	private int time;
	private int state;
	private Board board;
	private Player player;
	private double playerSize;
	
	public Game(Board board,double playerSize){
		this.playerSize = playerSize;
		this.board = board;
		player = new Player();
		time = 10*100;
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
		if (state == PLAYING){
			if (visionRange < 100) {
	            visionRange += 1;
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
		boolean xFirst = false;
		boolean yFirst = false;
		if (!keysPressed.isEmpty()){
			if (keysPressed.peek() == 's' || keysPressed.peek() == 'w'){
				yFirst = true;
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


		System.out.println(x
				+" "+ y);
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
