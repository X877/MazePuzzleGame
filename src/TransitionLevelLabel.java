import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Loads the transition label and refreshes the frame
 * Loads a different transition background
 * @author richietrang
 *
 */
public class TransitionLevelLabel extends JLabel {
	//Variables
	private JButton nextLevelBtn;
	private Image img;
	private String level;
	private int prevScores;
	private int levelScore;
	private JTextArea txtLevelScore;
	private JTextArea txtTotalScore;
	private JButton btnBackToMenu;
	
	/**
	 * 
	 * @param difficulty The difficulty of the new level
	 * @param frame The JFrame that needs to be revalidated
	 * @param levelScore The score of the level just played
	 * @param prevScores The score of the previous levels played
	 */
	public TransitionLevelLabel(int difficulty, JFrame frame, int levelScore, int prevScores) {
	    this.level = "";
        if (difficulty == 1) {
            this.level = "1";
        } else if (difficulty == 2) {
            this.level = "2";
        } else if (difficulty == 3) {
            this.level = "3";
        } else if (difficulty == 4) {
            this.level = "4";
        } else if (difficulty == 5){
        	this.level = "5";
        }
        //Set the background
        this.setText("");
		this.img = new ImageIcon(this.getClass().getResource("/images/transition" +  difficulty + ".png")).getImage();
        System.out.println("Level number is:" + level);
		this.setIcon(new ImageIcon(img));
		//Add the button
		
		this.prevScores = prevScores;
		System.out.println(prevScores);
		//Add the level score text
	   	txtLevelScore = new JTextArea();
		txtLevelScore.setText("Level Score: " + Integer.toString(levelScore));
		this.levelScore = levelScore;
		
		txtLevelScore.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		this.add(txtLevelScore);
		
		txtTotalScore = new JTextArea();
		//System.out.println(levelScore +" " + prevScores);
		int totalScore = levelScore + prevScores;
		prevScores = totalScore;
		
		txtTotalScore.setText("Total Score: " + totalScore ); //
		
		txtTotalScore.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		this.add(txtTotalScore);
		//System.out.println("prevScores rn is "  + prevScores);
		if(difficulty != 6){
		addNextLevelBtn(frame, prevScores);
		    if (level == "2"){
		        txtLevelScore.setBounds(650, 650, 300, 40);
		        txtTotalScore.setBounds(1000, 650, 300, 40);
		        nextLevelBtn.setBounds(925, 520, 200, 50);

		    } else if (level == "3"){
		        txtLevelScore.setBounds(1000, 500, 300, 40);
		        txtTotalScore.setBounds(1000, 600, 300, 40);
		        nextLevelBtn.setBounds(1045, 400, 200, 50);
		    } else if (level == "4"){
		        txtLevelScore.setBounds(30, 400, 280, 40);
		        txtTotalScore.setBounds(30, 500, 280, 40);
		        nextLevelBtn.setBounds(50, 600, 200, 50);
		    } else if (level == "5"){
		        txtLevelScore.setBounds(560, 200, 300, 40);
		        txtTotalScore.setBounds(560, 300, 300, 40);
		        checkScore();
		        addBackToMenuBtn(frame);
		    } 
		}
	}
	
    /**
     * Adds the next Level Button when called
     * @param frame The Jframe in which the label sits on. 
     * @param score The score of the level just played
     */
    public void addNextLevelBtn(final JFrame frame, final int score){
		nextLevelBtn = new JButton("Next Level");
		nextLevelBtn.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		add(nextLevelBtn);
		nextLevelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				//Create a new board
				LevelGenerator gennie = new LevelGenerator(Integer.parseInt(level));
				GUI nextLevel = gennie.levelGen(frame, score);
				frame.getContentPane().add(nextLevel);
				frame.setTitle("Don't be a HOBO - Stage " + level);
				frame.setLocationRelativeTo(null);
				frame.revalidate();
			}
		});
	}
    
    /**
     * gets the high score from the dat file named highscore.dat
     * @pre Called only if the player finished all 4 levels
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
	
	/**
	 * Adds the back to menu button
	 * @pre Only called if the player finished all four levels
	 * @param frame
	 */
	public void addBackToMenuBtn(final JFrame frame){
		btnBackToMenu = new JButton("Main Menu");
		btnBackToMenu.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
        btnBackToMenu.setBounds(560, 650, 300, 40);
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
	
	
	//if endTime + prevTime > checkScore()
    public void checkScore(){
    	String strOldScore = getHighScore();
    	int intOldScore = Integer.parseInt((strOldScore.split(":")[1]));
    	int newScore = levelScore + prevScores;
    	System.out.println("Enters here once?>>????");
    	if (levelScore + prevScores  > intOldScore){
		    String name = JOptionPane.showInputDialog("You set a new highscore! What is your name?");
		    if(name.length() > 0){
	            String highScore = name + ":" + newScore;
	            File scoreFile = new File("highscore.dat");
	            //does not exist
	            if(!scoreFile.exists()){
	    	        try {
	    	            scoreFile.createNewFile();
	    	        } catch(IOException e){
	        	        e.printStackTrace();
	                }
	            }

	            FileWriter writeFile = null;
	            BufferedWriter writer = null;
	            try{
	    	        writeFile = new FileWriter(scoreFile);
	    	        writer = new BufferedWriter(writeFile);
	    	        writer.write(highScore);
	            } catch (Exception e){
	    	    //errors if not found
	            } finally {
	    	        try{
	    	            if (writer != null){
	    		            writer.close();
	    	            }
	    	        } catch (Exception e){
	    	        }
	            }
		    }
    	}
	        
	}
}
