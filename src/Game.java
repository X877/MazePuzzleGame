import java.util.HashSet;

public class Game {
	// MOVEMENT PER TICK
	public static double MovementPerTick = 0.1;
	public static double playerSize = 0.4;
	public static final int PLAYING = 0;
	public static final int WON = 1;
	public static final int LOST = -1;
	private int time;
	private int state;
	private Board board;
	private Player player;
	
	public Game(Board board){
		this.board = board;
		player = new Player();
		time = 1000 * 1000;
		state = PLAYING;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void tick(){
		this.time -= GUI.tickTime;
		if (this.time < 0 && this.state == PLAYING){
			this.state = LOST;
		}
		System.out.println(time);
	}
	
	public void movePlayer(HashSet<Character> keysPressed){
		if (state != PLAYING){
			return;
		}
		double dx = 0;
		double dy = 0;
		double x = player.getX();
		double y = player.getY();
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
		
		//dx = roundDouble(dx);
		//dy = roundDouble(dy);
		
		int leftX = (int) roundDouble(x);
		int leftXNew = (int) roundDouble(x+dx);
		int rightX = (int) roundDouble(x + playerSize);
		int rightXNew = (int) roundDouble(x+playerSize+dx);
		int topY = (int) roundDouble(y);
		int topYNew = (int) roundDouble(y+dy);
		int bottomY  = (int) roundDouble(y + playerSize);
		int bottomYNew = (int) roundDouble(y+playerSize+dy);
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		if (currentTile.isEndPoint() && currentTile2.isEndPoint()){
			state = WON;
			return;
		}
		if (rightXNew >= board.getWidth()){
			dx = 0;
		}
		if (bottomYNew >= board.getHeight()){
			dy = 0;
		}
		if (dx != 0 && dy != 0){
			dx = roundDouble(dx/roundDouble(Math.sqrt(2)));
			dy = roundDouble(dy/roundDouble(Math.sqrt(2)));
		}
		if (dx > 0){
			if (rightX != rightXNew){
				if (topY != bottomY && board.getTile(rightXNew, topY).isWall(Tiles.NORTH)){
					
				}else if (!currentTile.isWall(Tiles.EAST) && !currentTile2.isWall(Tiles.EAST)){
					x += dx;
				}
			}else{
				x += dx;
			}
		}
		if (dx < 0){
			if (leftX != leftXNew){
				if (topY != bottomY && board.getTile(leftXNew, topY).isWall(Tiles.NORTH)){
					
				}else if (!currentTile.isWall(Tiles.WEST) && !currentTile2.isWall(Tiles.WEST)){
					x += dx;
				}
			}else{
				x += dx;
			}
		}
		leftX = (int) x;
		rightX = (int)(x+playerSize);
		
		
		if (dy > 0){
			if (bottomY != bottomYNew){
				if (leftX != rightX && board.getTile(rightX, bottomYNew).isWall(Tiles.WEST)){
					
				}else if (!currentTile.isWall(Tiles.NORTH) && !currentTile2.isWall(Tiles.NORTH)){
					y += dy;
				}
			}else{
				y += dy;
			}
		}
		if (dy < 0){
			if (topY != topYNew){
				if (leftX != rightX && board.getTile(rightX, topYNew).isWall(Tiles.WEST)){
					
				}else if (!currentTile.isWall(Tiles.SOUTH) && !currentTile2.isWall(Tiles.SOUTH)){
					y += dy;
				}
			}else{
				y += dy;
			}
		}
		//int x = 100*0.1;
		x = roundDouble(x);
		y = roundDouble(y);
		x = Math.max(x,0);
		y = Math.max(y,0);
		player.setXY(x,y);
		// Many code
	}
	
	double roundDouble(double x){
		
		return (double) Math.round(x * 100)/100;
	}
	public static double roundDouble2(double x){
		
		return (double) (int) (x * 10)/10;
	}
}
