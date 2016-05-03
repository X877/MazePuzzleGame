import javax.swing.*;
/**
 * Test for jframe for the apps
 */
public class MazeFrame extends JFrame{
    public MazeFrame() {

        initUI();
    }

    private void initUI() {

        final GUI testGUI = new GUI();
        add(testGUI);

        setTitle("MAZE");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
