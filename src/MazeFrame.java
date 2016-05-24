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

        PathGenerator newBoard = new PathGenerator(testBoard);
        newBoard.genMaze();

        GUI newGUI = new GUI(testBoard,30,30,30);
        add(newGUI);

        //BoardPanel boardPanel = new BoardPanel(testBoard);

        //add(boardPanel.createPanel());

        setTitle("MAZE");
        setSize(900, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
