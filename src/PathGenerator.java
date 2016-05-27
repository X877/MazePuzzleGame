import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Class that represents the generation of the maze and the powerups within the maze.
 * The PathGenerator class is also responsible for calculating the solution to the
 * maze, which is used for hints. 
 */
public class PathGenerator {
	
	private final static int[] dirX = new int[]{0, 1, 0, -1};
	private final static int[] dirY = new int[]{1, 0, -1, 0};
	private final static int powerUpRatio = 5;
	private final static int extraWallRatio = 15;
	
	private boolean[][] seen;
	private int width;
	private int height;
	private int bufferSpace;
	private Board board;
	private Random rand;
	
	/**
	 * Constructor for PathGenerator.
	 * <br> Takes a Board object and sets up variables used by other methods
	 * in PathGenerator.
	 * 
	 * @param startBoard - The board that PathGenerator modifies.
	 */
	public PathGenerator(Board startBoard){
		board = startBoard;
		width = board.getWidth();
		height = board.getHeight();
		seen = new boolean[width][height];
		rand = new Random();
		//START_END_SPACE includes both the space at the start and 
		//the end (both are equal in size).
		//We want to halve this as we care about the size of the buffer in one side only.
		bufferSpace = Board.START_END_SPACE/2;
	}

	/**
	 * A boolean function that returns True if the given point lies within the maze
	 * and false otherwise.
	 * @param curX - the X coordinate of the current point.
	 * @param curY - the Y coordinate of the current point.
	 * @return True if the point is in the bounds of the maze, false otherwise.
	 */
	private boolean isInBound(int curX, int curY){
		//The maze is surrounded by a buffer on the left and right sides, so we need to
		//accomodate for that in width.
		return (curX >= bufferSpace && curY >= 0 && curX < width-bufferSpace && curY < height);
	}
	
	/**
	 * Resets the seen array to all false values.
	 */
	private void resetSeen(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				seen[i][j] = false;
			}
		}
	}
	
	/**
	 * A recursive DFS that removes walls when visiting Tiles that have
	 * not been reached yet. This generates a tree in the graph of the maze, creating
	 * a basic maze structure that is guaranteed to have a path from the start to the end.
	 * @param curX - the X coordinate of the current point.
	 * @param curY - the Y coordinate of the current point.
	 */
	private void dfs(int curX, int curY){
		seen[curX][curY] = true;
		boolean[] dirSeen = new boolean[Tiles.NUMWALLS];
		int numDir = 0;

		while(numDir < Tiles.NUMWALLS){
			//randomly try a direction if this direction has not been seen before.
			int dir = rand.nextInt(Tiles.NUMWALLS);
			if(!dirSeen[dir]){
				dirSeen[dir] = true;
				numDir++;

				int newX = curX + dirX[dir];
				int newY = curY + dirY[dir];
				if(isInBound(newX, newY)){
					if(!seen[newX][newY]){
						if (!board.getTile(curX, curY).isStartPoint() && !board.getTile(curX, curY).isEndPoint()) {
							board.getTile(curX, curY).setWall(dir, false);
							/* We need remove the wall from the current tile and the corresponding
							 * wall in the new tile.
							 * The directions are ordered North, East, South and West.
							 * So adding 2 (mod the number of walls) to the current direction 
							 * gives the opposite direction
							 */
							board.getTile(newX, newY).setWall((dir + 2) % Tiles.NUMWALLS, false);
						}
						dfs(newX, newY);
					}
				}
			}
		}
	}
	
	/**
	 * Randomly adds powerups to the maze (accomplished by setting the state of the Tile object
	 * to have a powerup).
	 */
	private void addPowerUps(){
		resetSeen();
		int curX, curY; 
		//calculate the number of powerups to place within the maze.
		int numPowerUps = ((width-bufferSpace*2) * height)/powerUpRatio;
		while(numPowerUps > 0){
			//keep randomly generating a new position if the previous position 
			//has already been seen.
			do{
				curX = rand.nextInt(width - bufferSpace*2) + bufferSpace;
				curY = rand.nextInt(height);
			} while (seen[curX][curY]);
			
			//we have a new position, so set it to being seen.
			seen[curX][curY] = true;
			Tiles curTile = board.getTile(curX, curY);
			
			//We want more beers than other powerups. Beer is value 3, and value 0 is the
			//empty state, so we need to add one to the modded value so that it the 
			//correct powerup.
			if(numPowerUps%4 < 2){
				curTile.setState((numPowerUps%4) + 1);
			}
			else{
				//we generate beers half of the time a powerup is required.
				curTile.setState(Tiles.BEER);
			}
			numPowerUps--;
		}
	}
	
	/**
	 * Fixes the start and end spaces of the Maze so that all the walls are removed.
	 * <p>
	 * The implementation of the maze in the GUI is based upon tiles. We have a start and
	 * an end region on the left and right sides of the maze respectively, 
	 * where the player can move freely without walls, so we need to remove
	 * these walls in the Board object.
	 * <br>
	 * An additional 2 walls also need to be removed. The wall that connects the start region
	 * to the maze (at the bottom left corner of the maze) and the wall that connects
	 * the end region to the maze.
	 */
	private void startAndEnd(){
		//here we remove the wall that connects the maze to the start region
		Tiles startTile = board.getTile(bufferSpace, 0);
		startTile.setWall(Tiles.WEST, false);

		//here we remove the wall that connects the maze to the end region
		Tiles endTile = board.getTile((width-1)-bufferSpace, height-1);
		endTile.setWall(Tiles.EAST, false);
		
		//we go through all the tiles in both the start and end regions.
		//we remove all the walls except for those that form the border of the maze.
		for(int i = 0; i < bufferSpace; i++){
			for(int j = 0; j < height; j++){
				//handling the start region here.
				Tiles curTile = board.getTile(i, j);
				curTile.setNextTile(curTile);
				for(int k = 0; k < Tiles.NUMWALLS; k++){
					//here we check if the wall in the start region is 
					//part of the border of the maze.
					if(i == bufferSpace-1 && k == Tiles.EAST && j != 0){
						continue;
					}
					curTile.setWall(k, false);
				}
				
				//handling the end region here.
				curTile = board.getTile((width-1)-i, j);
				curTile.setNextTile(curTile);
				for(int k = 0; k < Tiles.NUMWALLS; k++){
					//here we check if the wall in the end region is 
					//part of the border of the maze.
					if(i == (width-bufferSpace) && k == Tiles.WEST && j != height-1){
						continue;
					}
					curTile.setWall(k, false);
				}
			}
		}
	}
	
	/**
	 * Removes extra walls (i.e. add extra edges in the graph)
	 * from the maze once it has been generated.
	 * <br>The generation algorithm creates a tree using a DFS. This means that
	 * there is only one path to the solution at any point. By removing some walls,
	 * we create cycles in the graph of the maze, so there are now multiple paths to
	 * the endpoint.
	 */
	private void removeWalls(){
		//calculate the number of extra walls to remove
		int numWalls = ((width-bufferSpace*2) * height)/extraWallRatio;
		while(numWalls > 0){
			boolean wallRemoved = false;
			/* get a random tile that is in bounds,
			 * in the rectangle bounded by the points (bufferSpace, 1)
			 * and (width - bufferSpace - 2, height - 2). This guarantees that
			 * newX and newY will also be in bound no matter what the chosen curX, curY
			 * or dir is.
			 */
			int curX = rand.nextInt(width - bufferSpace*2 - 2) + bufferSpace + 1;
			int curY = rand.nextInt(height - 2) + 1;
			int dir = rand.nextInt(Tiles.NUMWALLS);
			Tiles curTile = board.getTile(curX, curY);
			//check if the wall exists.
			if(curTile.isWall(dir)){
				int newX = curX + dirX[dir];
				int newY = curY + dirY[dir];
				board.getTile(curX, curY).setWall(dir, false);
				//remove the corresponding wall in the opposite 
				//direction (same idea as in the dfs code)
				board.getTile(newX, newY).setWall((dir + 2) % Tiles.NUMWALLS, false);
				wallRemoved = true;
			}
			
			//check if a wall was actually removed.
			if(wallRemoved){
				numWalls--;
			}
		}
	}
	
	/**
	 * Method that runs a BFS from the end point.
	 * <br>It sets the nextTile of each
	 * Tile object so that by following nextTile (pseudo linked list structure)
	 * you are guaranteed to reach the end point with the shortest path.
	 * <br>This is for the hint feature.
	 */
	private void generateSolution(){
		//reset the seen array for use in the BFS
		resetSeen();
		//starting at the endpoint. Need to adjust for the buferspace and indices.
		int startX = width-bufferSpace-1;
		int startY = height-1;
		seen[startX][startY] = true;
		Tiles startTile = board.getTile(startX, startY);
		//set the next tile of the endpoint to itself so that it is not NULL.
		startTile.setNextTile(startTile);
		Coordinates curPos = new Coordinates(startX, startY);
		
		//create a queue for the bfs and add the endpoint.
		Queue<Coordinates> bfs = new LinkedList<Coordinates>();
		bfs.add(curPos);
		
		while(!bfs.isEmpty()){
			curPos = bfs.remove();
			int curX = curPos.getX();
			int curY = curPos.getY();
			Tiles curTile = board.getTile(curX, curY);
			
			for(int dir = 0; dir < Tiles.NUMWALLS; dir++){
				int newX = curX + dirX[dir];
				int newY = curY + dirY[dir];
				if(isInBound(newX, newY)){
					//need to check if the edge exists (i.e. the wall does not exist).
					if(board.getTile(curX, curY).isWall(dir) == false){
						if(!seen[newX][newY]){
							seen[newX][newY] = true;
							Tiles newTile = board.getTile(newX, newY);
							/* As we are doing a BFS from the end, the tile that leads newTile to
							 * the end is curTile. 
							 */
							newTile.setNextTile(curTile);
							Coordinates newPos = new Coordinates(newX, newY);
							bfs.add(newPos);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method that generates the maze onto the board. <p>
	 * Generating the maze involves 4 steps:
	 * <br><b>1</b> - building a tree with a dfs (where the nodes are the tiles
	 * and edges are tiles are connected together and not blocked by a wall).
	 * <br><b>2</b> - removing extra walls from the maze so there are multiple paths
	 * to the end point.
	 * <br><b>3</b> - generating powerups on tiles in random positions.
	 * <br><b>4</b> - Solving the maze so that the hint functionality works.
	 * 
	 */
	public void genMaze(){
		//need to remove all the walls in the start and end spaces.
		startAndEnd();		
		int startX = rand.nextInt(width - bufferSpace*2);
		int startY = rand.nextInt(height);
		dfs(startX + bufferSpace, startY);
		
		removeWalls();
		
		addPowerUps();
		
		generateSolution();
	}
	
	public boolean[][] getSeen() {
		return seen;
	}

	public void setSeen(boolean[][] seen) {
		this.seen = seen;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}
}