import java.util.ArrayList;

/**
 *  Setting up an empty board with grids as an arraylist of an arraylist
 */
public class Board {
    private ArrayList<ArrayList<?>> columns;            //arraylist(board) containing collection arraylist(column) for the width and height to create grids
    private int height;                                 //replace ? with tiles(square with walls or whatever)
    private int width;

    public Board(int height, int width) {
        columns = new ArrayList<ArrayList<?>>();
        this.height = height;
        this.width = width;

        //adding columns to the board arraylist
        for(int i = 0; i < width; i++) {
            ArrayList<?> column = new ArrayList<>();
            columns.add(column);
        }

    }

}
