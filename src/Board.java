import java.util.ArrayList;

/**
 * Setting up an empty board with grids as an arraylist of an arraylist
 */
public class Board {
    public final static int START_END_SPACE = 4;        //HAS TO BE EVEN
    private ArrayList<ArrayList<Tiles>> columns;
    private int height;
    private int width;

    /**
     * Constructor of Board class
     * @param height height of the board
     * @param width width of the board
     */
    public Board(int height, int width) {
        columns = new ArrayList<ArrayList<Tiles>>();
        this.height = height;
        this.width = width + START_END_SPACE;

        //Adding columns to the board arraylist
        for (int i = 0; i < this.width; i++) {
            ArrayList<Tiles> column = new ArrayList<Tiles>();

            for (int j = 0; j < height; j++) {
                Tiles tile = new Tiles();

                //Adding start and end space
                //Checks to prevent 1 way wall at start and end
                if (i < START_END_SPACE/2 - 1) {
                    tile.setStartPoint(true);
                    tile.setWall(Tiles.NORTH, false);
                    tile.setWall(Tiles.EAST, false);
                    tile.setWall(Tiles.SOUTH, false);
                    tile.setWall(Tiles.WEST, false);
                } else if (i == START_END_SPACE/2 - 1) {
                    tile.setStartPoint(true);
                    tile.setWall(Tiles.NORTH, false);
                    tile.setWall(Tiles.SOUTH, false);
                    tile.setWall(Tiles.WEST, false);
                } else if (i == this.width - START_END_SPACE/2) {
                    tile.setEndPoint(true);
                    tile.setWall(Tiles.NORTH, false);
                    tile.setWall(Tiles.EAST, false);
                    tile.setWall(Tiles.SOUTH, false);
                } else if (i > this.width - START_END_SPACE/2) {
                    tile.setEndPoint(true);
                    tile.setWall(Tiles.NORTH, false);
                    tile.setWall(Tiles.EAST, false);
                    tile.setWall(Tiles.SOUTH, false);
                    tile.setWall(Tiles.WEST, false);
                }
                column.add(tile);
            }
            columns.add(column);
        }

    }

	public Tiles getTile(int x, int y){
		return columns.get(x).get(y);
	}
    
    public int getHeight() {
        return height;
    }

    
    public int getWidth() {
        return width;
    }


    public ArrayList<ArrayList<Tiles>> getColumns() {
        return columns;
    }

    /**
     * Method to remove hint from a tile
     */
	public void removeHint(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Tiles curTile = getTile(i, j);
				curTile.setHint(false);
			}
		}
	}

    /**
     * Method to show hint on a tile
     * @param playerX player coordinate X
     * @param playerY player coordinate Y
     */
	public void showHint(int playerX, int playerY){
		removeHint();
		Tiles curTile = getTile(playerX, playerY).getNextTile();
		for(int i = 0; i < 5; i++){
			curTile.setHint(true);
			curTile = curTile.getNextTile();
		}
	}
}
