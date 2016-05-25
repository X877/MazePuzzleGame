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
    public GUI levelGen() {
        PathGenerator path;
        GUI maze;
        switch (difficulty) {
            case 1:
                Board easyBoard = new Board(15, 25);
                path = new PathGenerator(easyBoard);
                path.genMaze();
                maze = new GUI(easyBoard, 25, 600, 290, 1);
                return maze;
            case 2:
                Board medBoard = new Board(25, 30);
                path = new PathGenerator(medBoard);
                path.genMaze();
                maze = new GUI(medBoard, 25, 690, 250, 2);
                return maze;
            case 3:
                Board hardBoard = new Board(33, 45);
                path = new PathGenerator(hardBoard);
                path.genMaze();
                maze = new GUI(hardBoard, 20, 715, 185, 3);
                return maze;

        }
        return null;
    }
}
