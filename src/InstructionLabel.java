import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public	class InstructionLabel extends JLabel{
	//Variables
	private JButton btnBackToMenu;
	Image img;
	
	/**
	 * Constructor Initialises the image of the instruction label and adds the button to go back to menu
	 * @param frame is the JFrame window. It needs to be altered and cleared
	 *        in order to make room for new labels and buttons.
	 */
	InstructionLabel(final JFrame frame){
		this.setText("");
		img = new ImageIcon(this.getClass().getResource("/image/wallpaper1.png")).getImage();
		this.setIcon(new ImageIcon(img));
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
}