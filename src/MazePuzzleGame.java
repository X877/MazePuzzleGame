import java.awt.*;

/**
 * Main class
 */
public class MazePuzzleGame {
    private Board board;

    public MazePuzzleGame(int height, int width) {
        board = new Board (height, width);
    }

    public static void main(String[] args) {
        //Testing JFrame, should be moved to GUI later
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                MazeFrame test = new MazeFrame();
                test.setVisible(true);
            }
        });
    }
}