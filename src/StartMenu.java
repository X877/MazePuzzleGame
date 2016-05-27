import javax.swing.*;
import java.awt.*;
/**
 * Used to initialise the program and the JFrame
 */
public class StartMenu {
	//Variables
	private JFrame frame;
    private JLabel menu;
	/**
	 * Launches the application.
	 * The main calls the constructor of a StartMenu JFrame
	 * @param args The arguments parsed into main. In this case there are none
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartMenu window = new StartMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor
	 * Initialises the JFrame and adds the contents of the JFrame
	 * Consisting of a JLabel of the start menu
	 */
	public StartMenu() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 1366, 768);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.menu =  new MenuLabel(frame);
		this.menu.setBounds(0, 0, 1366, 768);
		this.menu.setVisible(true);
		this.frame.setVisible(true);	
		this.frame.getContentPane().add(menu);
	}
}
