import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * Test for jframe for the apps
 */
public class MazeFrame extends JFrame{
    public MazeFrame() {

        initUI();
    }

    private void initUI() {
        Board testBoard = new Board(20, 20);

        PathGenerator newBoard = new PathGenerator(testBoard);
        newBoard.genMaze();

        JButton resetButton = new JButton("Reset Maze");
        GUI testGUI = new GUI(testBoard);
        add(resetButton, 0);
        add(testGUI, 1);

        resetButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();

                Board cleanBoard = new Board(20, 20);
                PathGenerator newNewBoard = new PathGenerator(cleanBoard);
                newNewBoard.genMaze();

                GUI newGUI = new GUI(cleanBoard);
                add(newGUI,1);
                setVisible(true);


            }
        });

        resetButton.setBounds(100, 100, 100, 50);

        setTitle("MAZE");
        setSize(900, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
