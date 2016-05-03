
public class Player {
	private int x;
	private int y;
	
	//Maybe change this
	public Player(){
		x = 0;
		y = 0;
	}
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void changeCoordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
}
