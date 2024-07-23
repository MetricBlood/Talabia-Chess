// Model class that stores data (use in save/load feature)
public class GameData {

    // Variable declaration
    private Piece[][] boardPiece;
    private boolean flipped;
    private int turn;
    private int moveCounter;

    // Receive data that needs to be stored and store it in this class variables
    public GameData(Piece[][] boardPiece, boolean flipped, int turn, int moveCounter) {
        this.boardPiece = boardPiece;
        this.flipped = flipped;
        this.turn = turn;
        this.moveCounter = moveCounter;
    }

    // To return the data in this class
    public Piece[][] getBoardPiece() {
        return this.boardPiece;
    }

    public boolean getFlipped() {
        return this.flipped;
    }

    public int getTurn() {
        return this.turn;
    }

    public int getMoveCounter() {
        return this.moveCounter;
    }
}
