import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Stores the game high score
 * The score is the sum of all leftover time from each level
 * Current implementation only stores one score
 */
public class HighScoresLabel extends JLabel {
    JButton btnBackToMenu;
    Image img;
    String highScore;
    JTextArea txtHighScore1;
	int currentScore;
    
	/**
	 * Constructor Initialises the image of the instruction label and adds the button to go back to menu
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	HighScoresLabel(final JFrame frame){
		this.setText("");
		img = new ImageIcon(this.getClass().getResource("/wallpaper3.png")).getImage();
		this.setIcon(new ImageIcon(img));
		setHighScore();
		addBackToMenuBtn(frame);
		frame.revalidate();
		highScore = getHighScore();		
	}
	
	/**
	 * Adds a button so you can return back to the main menu after reading instructions
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	public void addBackToMenuBtn(final JFrame frame){
		btnBackToMenu = new JButton("Main Menu");
		btnBackToMenu.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnBackToMenu.setBounds(583, 550, 200, 50);
		
		add(btnBackToMenu);
		
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuLabel menu = new MenuLabel(frame);
				menu.setVisible(true);	
				frame.getContentPane().removeAll();
			    frame.getContentPane().add(menu);
				frame.revalidate();
			}
		});
	}
	
	/**
	 * Sets the highScore text
	 * @param txtHighScore1 The JTextArea that stores the score
	 * @post txtHighScore1 must be in the form name:score
	 */
	public void setHighScore(){
		txtHighScore1 = new JTextArea();
		txtHighScore1.setText(getHighScore());
		txtHighScore1.setBounds(583, 200, 200, 40);
		txtHighScore1.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		this.add(txtHighScore1);		
	}
    
	/**
	 * Gets the high score from the dat file if there
	 * Uses a dat rather than txt file to help reduce cheating
	 * @pre If there is no dat file (no score set) 
	 *      then it displays nobody:0
	 */
	public String getHighScore(){
		//Format: Name:score
		FileReader readFile = null;
		BufferedReader reader = null;
		try{
		    readFile = new FileReader("highscore.dat");
		    reader = new BufferedReader(readFile);
		    //Returns the score as a string
		    return reader.readLine();
		} catch (Exception e){
			//No name or high score so return 0
			return "Nobody:0";
		} finally{
			try {
				//Close the reader if it is not null
				if(reader != null){
					reader.close();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
}
