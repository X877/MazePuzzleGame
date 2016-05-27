import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class PathGenerator {
	
	private final static int[] dirX = new int[]{0, 1, 0, -1};
	private final static int[] dirY = new int[]{1, 0, -1, 0};
			
	private boolean[][] seen;
	private int width;
	private int height;
	private int bufferSpace;
	private Board board;
	private Random rand;
	
	public PathGenerator(Board startBoard){
		board = startBoard;
		width = board.getWidth();
		height = board.getHeight();
		seen = new boolean[width][height];
		rand = new Random();
		bufferSpace = Board.START_END_SPACE/2;
	}

	private boolean isInBound(int curX, int curY){
		return (curX >= bufferSpace && curY >= 0 && curX < width-bufferSpace && curY < height);
	}
	
	private void resetSeen(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				seen[i][j] = false;
			}
		}
	}
	
	private void dfs(int curX, int curY){
		seen[curX][curY] = true;
		boolean[] dirSeen = new boolean[Tiles.NUMWALLS];
		int numDir = 0;

		while(numDir < 4){
			int dir = rand.nextInt(4);
			if(!dirSeen[dir]){
				dirSeen[dir] = true;
				numDir++;

				int newX = curX + dirX[dir];
				int newY = curY + dirY[dir];
				if(isInBound(newX, newY)){
					if(!seen[newX][newY]){
						if (!board.getTile(curX, curY).isStartPoint() && !board.getTile(curX, curY).isEndPoint()) {
							board.getTile(curX, curY).setWall(dir, false);
							//The weird mod thing gives the opposite direction.
							board.getTile(newX, newY).setWall((dir + 2) % Tiles.NUMWALLS, false);
						}
						dfs(newX, newY);
					}
				}
			}
		}
	}
	
	private void addPowerUps(){
		resetSeen();
		//Tiles[][] prevTile = new Tiles[width][height];
		int curX, curY; 
		int numPowerUps = ((width-bufferSpace*2) * height)/5;
		while(numPowerUps > 0){
			do{
				curX = rand.nextInt(width - bufferSpace*2) + bufferSpace;
				curY = rand.nextInt(height);
			} while (seen[curX][curY]);
			seen[curX][curY] = true;
			Tiles curTile = board.getTile(curX, curY);
			curTile.setState((numPowerUps%3) + 1);
			numPowerUps--;
		}
	}
	
	private void startAndEnd(){
		Tiles startTile = board.getTile(bufferSpace, 0);
		startTile.setWall(Tiles.WEST, false);
		
		Tiles endTile = board.getTile((width-1)-bufferSpace, height-1);
		endTile.setWall(Tiles.EAST, false);
		
		for(int i = 0; i < bufferSpace; i++){
			for(int j = 0; j < height; j++){
				Tiles curTile = board.getTile(i, j);
				curTile.setNextTile(curTile);
				for(int k = 0; k < 4; k++){
					if(i == bufferSpace-1 && k == Tiles.EAST && j != 0){
						continue;
					}
					curTile.setWall(k, false);
				}
				
				curTile = board.getTile((width-1)-i, j);
				curTile.setNextTile(curTile);
				for(int k = 0; k < 4; k++){
					if(i == bufferSpace-1 && k == Tiles.WEST && j != height-1){
						continue;
					}
					curTile.setWall(k, false);
				}
			}
		}
	}
	
	private void addEdges(){
		int numEdges = ((width-bufferSpace*2) * height)/15;
		while(numEdges > 0){
			boolean edgeRemoved = false;
			int curX = rand.nextInt(width - bufferSpace*2 - 2) + bufferSpace + 1;
			int curY = rand.nextInt(height - 2) + 1;
			int dir = rand.nextInt(Tiles.NUMWALLS);
			Tiles curTile = board.getTile(curX, curY);
			if(curTile.isWall(dir)){
				int newX = curX + dirX[dir];
				int newY = curY + dirY[dir];
				board.getTile(curX, curY).setWall(dir, false);
				board.getTile(newX, newY).setWall((dir + 2) % Tiles.NUMWALLS, false);
				edgeRemoved = true;
			}
			
			if(edgeRemoved){
				numEdges--;
			}
		}
	}
	
	private void generateSolution(){
		resetSeen();
		int startX = width-bufferSpace-1;
		int startY = height-1;
		seen[startX][startY] = true;
		Tiles startTile = board.getTile(startX, startY);
		startTile.setNextTile(startTile);
		Coordinates curPos = new Coordinates(startX, startY);
		
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
					if(board.getTile(curX, curY).isWall(dir) == false){
						if(!seen[newX][newY]){
							seen[newX][newY] = true;
							Tiles nextTile = board.getTile(newX, newY);
							nextTile.setNextTile(curTile);
							Coordinates nextPos = new Coordinates(newX, newY);
							bfs.add(nextPos);
						}
					}
				}
			}
		}
	}
	
	public void genMaze(){
		//need to remove all the walls in the start and end spaces.
		startAndEnd();		
		int startX = rand.nextInt(width - bufferSpace*2);
		int startY = rand.nextInt(height);
		dfs(startX + bufferSpace, startY);
		addEdges();
		addPowerUps();
		generateSolution();
	}
	
	public void removeHint(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Tiles curTile = board.getTile(i, j);
				curTile.setHint(false);
			}
		}
	}
	
	public void showHint(int playerX, int playerY){
		removeHint();
		Tiles curTile = board.getTile(playerX, playerY).getNextTile();
		for(int i = 0; i < 5; i++){
			curTile.setHint(true);
			curTile = curTile.getNextTile();
		}
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
