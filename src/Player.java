
/**
 * Contains all the data needed for a player, which essentially acts to store coordinates and movement direction
 *
 */
public class Player {	
	public static final int LEFT = 4;
	public static final int RIGHT = 3;
	public static final int UP = 2;
	public static final int DOWN = 1;
	public static final int NONE = 0;
	private double x;
	private double y;
	private int direction;
	
	/**
	 * Sets coordinates and direction to 0 and none respectively
	 */
	
	public Player(){
		x = 0;
		y = 0;
		direction = NONE;
	}
	
	/**
	 * @return X coordinate of player
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * @return Y coordinate of player
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * @return Direction player is moving
	 */
	public int getDirection(){
		return direction;
	}
	
	/**
	 * Changes player direction
	 * @param direction direction to change to
	 */
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	/**
	 * Changes coordinate of player
	 * @param x new X coordinate
	 * @param y new Y coordinate
	 */
	public void setXY(double x, double y){
		this.x = x;
		this.y = y;
	}
}
