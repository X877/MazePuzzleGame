import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScoresLabel extends JLabel {
    JButton btnBackToMenu;
    Image img;
    double score1;
    double score2;
    double score3;
    JTextArea txtHighScore1;
    JTextArea txtHighScore2;
    JTextArea txtHighScore3;
    
	/**
	 * Constructor Initialises the image of the instruction label and adds the button to go back to menu
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	HighScoresLabel(final JFrame frame){
		this.setText("");
		img = new ImageIcon(this.getClass().getResource("/image/wallpaper3.png")).getImage();
		this.setIcon(new ImageIcon(img));
		setHighScore();
		addBackToMenuBtn(frame);
		
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
		txtHighScore1.setText("Richie 100.04s");
		txtHighScore1.setBounds(583, 200, 200, 40);
		txtHighScore1.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));

		txtHighScore2 = new JTextArea();
		txtHighScore2.setText("Daaron 120.08s");
		txtHighScore2.setBounds(583, 300, 200, 40);
		txtHighScore2.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		
		txtHighScore3 = new JTextArea();
		txtHighScore3.setText("Lachlan 200.05s");
		txtHighScore3.setBounds(583, 400, 200, 40);
		txtHighScore3.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		
		this.add(txtHighScore1);
		this.add(txtHighScore2);
		this.add(txtHighScore3);
	}

	
	
	/**
	 * Alters the high scores if a time is faster
	 * @param time The sum of the time after passing all three levels
	 */
	public void changeHighScore(String name, int time){
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
}