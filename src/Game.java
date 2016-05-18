import java.util.HashSet;

public class Game {
	// MOVEMENT PER TICK
	public static double MovementPerTick = 0.1;
	public static double playerSize = 0.4;
	private Board board;
	private Player player;
	
	public Game(Board board){
		this.board = board;
		player = new Player();
	}
	
	public Board getBoard(){
		return board;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void movePlayer(HashSet<Character> keysPressed){
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
		int leftX = (int) x;
		int leftXNew = (int) (x+dx);
		int rightX = (int) (x + playerSize);
		int rightXNew = (int) (x+playerSize+dx);
		int topY = (int) y;
		int topYNew = (int) (y+dy);
		int bottomY  = (int) (y + playerSize);
		int bottomYNew = (int) (y+playerSize+dy);
		Tiles currentTile = board.getTile(leftX, topY);
		Tiles currentTile2 = board.getTile(rightX, bottomY);
		if (rightXNew >= board.getWidth()){
			dx = 0;
		}
		if (bottomYNew >= board.getHeight()){
			dy = 0;
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
		
		x = Math.max(x,0);
		y = Math.max(y,0);
		player.setXY(x,y);
		// Many code
	}
}
