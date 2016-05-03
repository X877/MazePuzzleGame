//Simple learning tutorial for JFrame
//Can be used to help the team understand JFrame
// by Richie

import java.awt.*;
import java.awt.event.*;

//Frame for GUI
import javax.swing.JFrame;
// Text inside the JFrame
import javax.swing.JLabel;
//Block inside the JFrame
import javax.swing.JPanel;
//Button inside the JFrame
import javax.swing.JButton;

public class GUI {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msgLabel;
    
	public GUI() {
		prepareGUI();
	}
	
	public static void main(String[] args){
		GUI firstGUI = new GUI();
		firstGUI.showGUI();
	}
	private void showGUI() {
	      headerLabel.setText("Container in action: JFrame");   

	      final JFrame frame = new JFrame();
	      frame.setSize(400, 400);
	      frame.setLayout(new FlowLayout());       
	      frame.add(msgLabel);
	      frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            frame.dispose();
	         }        
	      });    
	      JButton okButton = new JButton("Start");
	      okButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            statusLabel.setText("A Frame shown to the user.");
	            frame.setVisible(true);
	         }
	      });
	      controlPanel.add(okButton);
	      mainFrame.setVisible(true);  
	   }
		

	private void prepareGUI() {
		mainFrame = new JFrame("Java Swing Examples");
		//set size using pixels
	    mainFrame.setSize(400,400);
	    mainFrame.setLayout(new GridLayout(3, 1));
	    mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	    headerLabel = new JLabel("", JLabel.CENTER);        
	    statusLabel = new JLabel("",JLabel.CENTER);    

	    statusLabel.setSize(350,100);

	    msgLabel = new JLabel("Welcome to TutorialsPoint SWING Tutorial.", JLabel.CENTER);
	    controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());

	    mainFrame.add(headerLabel);
	    mainFrame.add(controlPanel);
	    mainFrame.add(statusLabel);
	    mainFrame.setVisible(true);
		
	}

}
