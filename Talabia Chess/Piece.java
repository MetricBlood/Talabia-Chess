// Model class to represents the base class for all the game pieces
public abstract class Piece {

    // Variable declaration
    private String color;

    // Constructor for piece class
    public Piece(String color) {
        this.color = color;
    }

    // Get the color of the piece
    public String pieceColor() {
        return color;
    }

    // Check if a move from the current position to the target position is valid
    public abstract boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board);

    // To get the icon name of the piece
    public abstract String getIconName();
}