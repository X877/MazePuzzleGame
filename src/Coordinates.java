
/**
 * Class that represents a coordinate in 2 dimensions.<br>
 * The X value corresponds to horizontal movement of the game, and the Y value
 * corresponds to the vertical movement.
 */
public class Coordinates{
	private int x;
	private int y;

	public Coordinates(int startX, int startY){
		x = startX;
		y = startY;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
