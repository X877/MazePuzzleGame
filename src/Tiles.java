import java.awt.*;

/**
 * Class that represents a tile within the maze. Each tile has 4 walls in the 4 directions
 * of the 2D plane (north, south, east, west) and also contains a state which represents
 * whether the tile has a powerup on it and what powerup it has as well as a separate state
 * to show whether this tile should display a hint image.
 */
public class Tiles {
	//Static values that represent constants used in the code.
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int SOUTH = 2;
	public final static int WEST = 3;
	public final static int NUMWALLS = 4;
	public final static int EMPTY = 0;
	public final static int COFFEE = 1;
	public final static int BOOK = 2;
	public final static int BEER = 3;
	
	/**
	 * boolean array that holds the status of the walls (whether they exist or not).
	 */
	private boolean[] walls;
    private boolean hasPlayer;
    private boolean startPoint;
	private boolean endPoint;
	/**
	 * An Image type that stores the image that represents the tile (used in the GUI).
	 */
    private Image tile;
    /**
     * Variable that represents the state of the tile. An EMPTY value means it has no powerup,
     * otherwise it contains either COFFEE, BOOK or BEER.
     */
    private int state;
    /**
     * Variable that is used to check whether a hint image should be displayed on this tile.
     */
    private boolean hint;
    /**
     * A pointer to the nextTile in the shortest path to the endpoint. (used to generate the
     * hint trail).
     */
    private Tiles nextTile;

    /**
     * Constructor for Tiles class
     * All the tiles will be created with all the walls in existence.
     */
    public Tiles() {
        walls = new boolean[NUMWALLS];

		for(int curWall = 0; curWall < NUMWALLS; curWall++){
			this.walls[curWall] = true;
		}
        this.hasPlayer = false;
        this.startPoint = false;
        this.endPoint = false;
        this.state = EMPTY;
        //this.tile = new ImageIcon(this.getClass().getResource("/Tile_All.png")).getImage();
    }

    /**
     * Checks if a wall exists.
     * @param wallDirection - The direction of the wall that is to be checked.
     * @return True if the wall exists, false it it does not.
     */
	public boolean isWall(int wallDirection){
		return walls[wallDirection];
	}

	/**
	 * Set the status of a given wall.
	 * @param wallDirection - The direction of the wall to set.
	 * @param value - The value to set the wall to.
	 */
	public void setWall(int wallDirection, boolean value){
		walls[wallDirection] = value;
	}

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public boolean isStartPoint() {
        return startPoint;
    }

    public void setStartPoint(boolean startPoint) {
        this.startPoint = startPoint;
    }

    public boolean isEndPoint() {
        return endPoint;
    }

    public void setEndPoint(boolean endPoint) {
        this.endPoint = endPoint;
    }

    public Image getTile() {
        return tile;
    }

    public void setTile(Image tile) {
        this.tile = tile;
    }
    
    public void setState(int newState){
    	this.state = newState;
    }
    
    public int getState(){
    	return state;
    }
    
    public void setHint(boolean value){
    	hint = value;
    }
    
    public boolean getHint(){
    	return hint;
    }
    
    public Tiles getNextTile(){
    	return nextTile;
    }
    
    public void setNextTile(Tiles tile){
    	nextTile = tile;
    }
}
