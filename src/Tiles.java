/**
 * Used to create walls on the maze board and locate player
 */
public class Tiles {
	static public int north = 0;
	static public int east = 1;
	static public int south = 2;
	static public int west = 3;
	static public int numWalls = 4;
	private boolean[4] walls;
    private boolean hasPlayer;
    private boolean startPoint;
	private boolean endPoint;

    /**
     * Constructor for Tiles class
     * All the tiles will be created as a closed
     * tiles by default
     */
    public Tiles() {
		for(int curWall = 0; curWall < numWalls; curWall++){
			this.walls[curWall] = true;
		}
        this.hasPlayer = false;
        this.startPoint = false;
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
}
