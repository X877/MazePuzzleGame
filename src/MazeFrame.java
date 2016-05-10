import javax.swing.*;
/**
 * Test for jframe for the apps
 */
public class MazeFrame extends JFrame{
    public MazeFrame() {

        initUI();
    }

    private void initUI() {
        Board testBoard = new Board(20, 20);

        GUI testGUI = new GUI(testBoard);
        add(testGUI);

        setTitle("MAZE");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
