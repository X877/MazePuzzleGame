import java.awt.*;

/**
 * Used to create walls on the maze board and locate player
 */
public class Tiles {
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int SOUTH = 2;
	public final static int WEST = 3;
	public final static int NUMWALLS = 4;
	public final static int EMPTY = 0;
	public final static int BEER = 1;
	public final static int COFFEE = 2;
	public final static int BOOK = 3;
	
	
	private boolean[] walls;
    private boolean hasPlayer;
    private boolean startPoint;
	private boolean endPoint;
    private Image tile;
    private int state;

    /**
     * Constructor for Tiles class
     * All the tiles will be created as a closed
     * tiles by default
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

	public boolean isWall(int wallDirection){
		return walls[wallDirection];
	}

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
}
