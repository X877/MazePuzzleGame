import java.util.Random;

public class PathGenerator {
	
	boolean[][] seen;
	int width;
	int height;
	Board board;
	Random rand;

	public PathGenerator(Board startBoard){
		board = startBoard;
		width = board.getWidth();
		height = board.getHeight();
		seen = new boolean[width][height];
		rand = new Random();
	}

	private boolean isInBound(int curX, int curY){
		return (curX >= 0 && curY >= 0 && curX < width && curY < height);
	}
	
	private void dfs(int curX, int curY){
		seen[curX][curY] = true;
		int[] dirX = new int[]{0, 1, 0, -1};
		int[] dirY = new int[]{1, 0, -1, 0};
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
						board.getTile(curX, curY).setWall(dir, false);
						//The weird mod thing gives the opposite direction.
						board.getTile(newX, newY).setWall((dir+2)%Tiles.NUMWALLS, false);
						dfs(newX, newY);
					}
				}
			}
		}
	}
	
	public void genMaze(){		
		int startX = rand.nextInt(width);
		int startY = rand.nextInt(height);
		dfs(startX, startY);
	}

}
