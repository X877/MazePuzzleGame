import javax.swing.JFrame;

/**
 * Class to generate a maze board depending
 * on the difficulties
 */
public class LevelGenerator {

    private int difficulty;

    public LevelGenerator (int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Method to create board GUI based on difficulty
     * @return generated level GUI
     */
    public GUI levelGen(final JFrame frame) {
        PathGenerator path;
        GUI maze;
        switch (difficulty) {
            case 1:
                Board easyBoard = new Board(16, 36);
                path = new PathGenerator(easyBoard);
                path.genMaze();
                maze = new GUI(easyBoard, 27, 607, 135, 1, frame);
                return maze;
            case 2:
                Board medBoard = new Board(20, 36);
                path = new PathGenerator(medBoard);
                path.genMaze();
                maze = new GUI(medBoard, 27, 720, 135, 2, frame);
                return maze;
            case 3:
                Board hardBoard = new Board(30, 45);
                path = new PathGenerator(hardBoard);
                path.genMaze();
                maze = new GUI(hardBoard, 22, 715, 135, 3, frame);
                return maze;
            case 4:
                Board expertBoard = new Board(30, 45);
                path = new PathGenerator(expertBoard);
                path.genMaze();
                maze = new GUI(expertBoard, 22, 715, 135, 4, frame);
                return maze;

        }
        return null;
    }
}
