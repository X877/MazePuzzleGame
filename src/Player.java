import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	public static final int LEFT = 4;
	public static final int RIGHT = 3;
	public static final int UP = 2;
	public static final int DOWN = 1;
	public static final int NONE = 0;
	private double x;
	private double y;
	private int direction;
	
	//Maybe change this
	public Player(){
		//leftImages = new Image[2];
		//leftImages[0] = new ImageIcon(this.getClass().getResource("/Player_Left_1.png")).getImage();
		//leftImages[1] = new ImageIcon(this.getClass().getResource("/Player_Left_2.png")).getImage();
		x = 35;
		y = 15;
	}
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public void setXY(double x, double y){
		this.x = x;
		this.y = y;
	}
}
