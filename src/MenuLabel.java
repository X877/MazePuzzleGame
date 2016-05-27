import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//import java.awt.event.KeyAdapter;
//Add enter button to play later

public class MenuLabel extends JLabel {
	//Variables
	private Image img;
	private JButton btnInstructions;
	private JButton btnHighScores;
	private JButton btnCredits;
	private JButton btnPlay;
	/**
	 * Constructor Initialises the background image, sets the size and adds buttons
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	MenuLabel(final JFrame frame){
		this.setText("");
		this.setBounds(0, 0, 1376, 768);
		img = new ImageIcon(this.getClass().getResource("/images/Menus/Main.png")).getImage();
		this.setIcon(new ImageIcon(img));
		addPlayButton(frame);
		addHighScoresButton(frame);
		addInstructionButton(frame);
		addCreditsButton(frame);
	}
	
	/**
	 * Adds the play button and ensure the button opens up the maze panel
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	public void addPlayButton(final JFrame frame){
		btnPlay = new JButton("play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				//Create a new board
				LevelGenerator gennie = new LevelGenerator(2);
				GUI easy = gennie.levelGen(frame);

				frame.getContentPane().add(easy);
				frame.setTitle("Don't be a HOBO - Stage 1");
				frame.setLocationRelativeTo(null);
				frame.revalidate();
			}
		});
      
		//Allows the game to start by simply pressing enter or space bar
	    btnPlay.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				//VK_ENTER is synonymous with VK_SPACE
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					frame.getContentPane().removeAll();
					frame.revalidate();

					//Create a new board

					LevelGenerator gennie = new LevelGenerator(1);
					GUI easy = gennie.levelGen(frame);

					frame.getContentPane().add(easy);
					frame.setTitle("Don't be a HOBO - Stage 1");
					frame.setSize(1366, 768);
					frame.setVisible(true);
					easy.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.revalidate();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}
		});
	    
	    
		btnPlay.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnPlay.setBounds(320, 370, 200, 50);
		this.add(btnPlay);
	}
	
	/**
	 * Adds the high scores button and ensures the button opens up the high scores label
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	public void addHighScoresButton(final JFrame frame){
		btnHighScores = new JButton("high scores");
		btnHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HighScoresLabel labelHighScores = new HighScoresLabel(frame);
				labelHighScores.setVisible(true);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(labelHighScores);
				frame.revalidate();
			}
		});
		btnHighScores.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnHighScores.setBounds(580, 370, 200, 50);
		this.add(btnHighScores);
	}
	
	/**
	 * Adds the instructions button and ensures the button opens up the instructions label
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	public void addInstructionButton(final JFrame frame){
		//Instructions
				btnInstructions = new JButton("instructions");
				btnInstructions.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						InstructionLabel labelInstructions = new InstructionLabel(frame);
						labelInstructions.setVisible(true);
						frame.getContentPane().removeAll();
					    frame.getContentPane().add(labelInstructions);
						frame.revalidate();
					}
				});
				btnInstructions.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
				btnInstructions.setBounds(320, 460, 200, 50);
				this.add(btnInstructions);

	}
	
	/**
	 * Adds the credits button and ensure the button opens up the credits label
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	public void addCreditsButton(final JFrame frame){
		btnCredits = new JButton("credits");
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreditsLabel labelCredits = new CreditsLabel(frame);
				labelCredits.setVisible(true);
				frame.getContentPane().removeAll();
			    frame.getContentPane().add(labelCredits);
				frame.revalidate();
			}
		});
		btnCredits.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnCredits.setBounds(580 ,460, 200, 50);
		this.add(btnCredits);
	}

}

	


