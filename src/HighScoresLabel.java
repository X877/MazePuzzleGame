import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class HighScoresLabel extends JLabel {
    JButton btnBackToMenu;
    Image img;
    String highScore;
    //String score2;
    //String score3;
    JTextArea txtHighScore1;
    //JTextArea txtHighScore2;
    //JTextArea txtHighScore3;
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
	
	public void setHighScore(){
		txtHighScore1 = new JTextArea();
		txtHighScore1.setText(getHighScore());
		txtHighScore1.setBounds(583, 200, 200, 40);
		txtHighScore1.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		this.add(txtHighScore1);
        /*
		txtHighScore2 = new JTextArea();
		txtHighScore2.setText("Daaron 120.08s");
		txtHighScore2.setBounds(583, 300, 200, 40);
		txtHighScore2.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		
		txtHighScore3 = new JTextArea();
		txtHighScore3.setText("Lachlan 200.05s");
		txtHighScore3.setBounds(583, 400, 200, 40);
		txtHighScore3.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));

		this.add(txtHighScore2);
		this.add(txtHighScore3);
		
		*/
		
		
		
	}
    
	
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
	
	
	/**
	 * Alters the high scores if a time is faster
	 * @param time The sum of the time after passing all three levels
	 */
	
	
	
	/*
	public void changeHighScore(String newScore){
		
		//The fastest time should be stored as time1
		if (time < score1){
			score1 = time;
		//A time faster than score 2 but slower than score 3 should be the new score2
		} else if( time < score2 && time > score3){
			score2 = time;
		//If the time is faster than score 3 it becomes new score 3
		} else if (time < score3){
			score3 = time;
		}
	}
	*/
}