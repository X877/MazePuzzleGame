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

        //test.setWall(1, false);
        //test.setWall(2, false);
        //test.setWall(3, false);

        PathGenerator newBoard = new PathGenerator(testBoard);
        newBoard.genMaze();

        GUI testGUI = new GUI(testBoard);
        add(testGUI);

        setTitle("MAZE");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
