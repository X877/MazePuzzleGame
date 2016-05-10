/**
 * Used to create walls on the maze board and locate player
 */
public class Tiles {
    private boolean wallN;
    private boolean wallE;
    private boolean wallS;
    private boolean wallW;
    private boolean hasPlayer;
    private boolean startPoint;

    /**
     * Constructor for Tiles class
     * All the tiles will be created as a closed
     * tiles by default
     */
    public Tiles() {
        this.wallN = true;
        this.wallE = true;
        this.wallS = true;
        this.wallW = true;
        this.hasPlayer = false;
        this.startPoint = false;
    }

    public boolean isWallN() {
        return wallN;
    }

    public void setWallN(boolean wallN) {
        this.wallN = wallN;
    }

    public boolean isWallE() {
        return wallE;
    }

    public void setWallE(boolean wallE) {
        this.wallE = wallE;
    }

    public boolean isWallS() {
        return wallS;
    }

    public void setWallS(boolean wallS) {
        this.wallS = wallS;
    }

    public boolean isWallW() {
        return wallW;
    }

    public void setWallW(boolean wallW) {
        this.wallW = wallW;
    }

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public boolean isStartPoint() {
        return startPoint;
    }

    public void setStartPoint(boolean startPoint) {
        this.startPoint = startPoint;
    }
}
