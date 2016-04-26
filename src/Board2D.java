/**
 * Setup board with 2d arrays of walls
 */
public class Board2D {
	private boolean[][] verticalWalls;
	private boolean[][] horizontalWalls;
	private int width;
	private int height;
	
	public Board2D(int width, int height){
		horizontalWalls = new boolean[width][height];
		verticalWalls = new boolean[width][height];
		this.width = width;
		this.height = height;
		
		//Automatically marks all walls as existing (creates a grid of width*height)
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				horizontalWalls[i][j] = true;
				verticalWalls[i][j] = true;
			}
		}
	}
}
