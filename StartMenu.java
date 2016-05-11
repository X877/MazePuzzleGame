import java.awt.EventQueue;
public class StartMenu {

	private JFrame frame;
	private JButton btnInstructions;
	private JLabel background;

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public StartMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		background = new JLabel("");
		background.setBounds(0, 0, 900, 600);
		Image img = new ImageIcon(this.getClass().getResource("/background.png")).getImage();
	    background.setIcon(new ImageIcon(img));
	    frame.setContentPane(background);
		
		JButton btnPlay = new JButton("play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlay.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnPlay.setBounds(320, 370, 200, 50);
		frame.getContentPane().add(btnPlay);
		
		btnInstructions = new JButton("instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInstructions.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnInstructions.setBounds(320, 460, 200, 50);
		frame.getContentPane().add(btnInstructions);
		
		//btnInstructions.addActionListener(new InstructionAction());

	
		
		
		JButton btnHighScores = new JButton("high scores");
		btnHighScores.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnHighScores.setBounds(580, 370, 200, 50);
		frame.getContentPane().add(btnHighScores);
		
		JButton btnCredits = new JButton("credits");
		btnCredits.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		btnCredits.setBounds(580 ,460, 200, 50);
		frame.getContentPane().add(btnCredits);
	}
}
