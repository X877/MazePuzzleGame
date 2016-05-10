import java.util.ArrayList;

/**
 * Setting up an empty board with grids as an arraylist of an arraylist
 */
public class Board {
    private ArrayList<ArrayList<Tiles>> columns;
    private int height;
    private int width;

    public Board(int height, int width) {
        columns = new ArrayList<ArrayList<Tiles>>();
        this.height = height;
        this.width = width;

        //adding columns to the board arraylist
        for (int i = 0; i < width; i++) {
            ArrayList<Tiles> column = new ArrayList<Tiles>();

            for (int j = 0; j < height; j++) {
                Tiles tile = new Tiles();
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
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
        

}
