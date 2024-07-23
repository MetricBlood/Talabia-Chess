// Model class to set up the game board
public class Board {

    // Variable declaration
    private Piece[][] boardPiece;
    public boolean flipped = false;

    // Constructor: Initializes the board and sets up the initial pieces
    public Board() {
        this.boardPiece = new Piece[6][7];
        initializeBoard();
    }

    // Getter for the board piece
    public Piece[][] getBoardPiece() {
        return boardPiece;
    }

    // Getter for the boolean flipped
    public boolean getFlipped() {
        return flipped;
    }

    // Setter for the board piece
    public void setBoardPiece(Piece[][] boardPiece) {
        this.boardPiece = boardPiece;
    }

    // Setter for the boolean flipped
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    // Initialize the chess board with the initial arrangement of pieces
    public void initializeBoard() {
        // Yellow pieces
        boardPiece[4][0] = new Point("Yellow");
        boardPiece[4][1] = new Point("Yellow");
        boardPiece[4][2] = new Point("Yellow");
        boardPiece[4][3] = new Point("Yellow");
        boardPiece[4][4] = new Point("Yellow");
        boardPiece[4][5] = new Point("Yellow");
        boardPiece[4][6] = new Point("Yellow");
        boardPiece[5][0] = new Plus("Yellow");
        boardPiece[5][6] = new Plus("Yellow");
        boardPiece[5][1] = new Hourglass("Yellow");
        boardPiece[5][5] = new Hourglass("Yellow");
        boardPiece[5][2] = new Time("Yellow");
        boardPiece[5][4] = new Time("Yellow");
        boardPiece[5][3] = new Sun("Yellow");

        // Blue pieces
        boardPiece[1][0] = new Point("Blue");
        boardPiece[1][1] = new Point("Blue");
        boardPiece[1][2] = new Point("Blue");
        boardPiece[1][3] = new Point("Blue");
        boardPiece[1][4] = new Point("Blue");
        boardPiece[1][5] = new Point("Blue");
        boardPiece[1][6] = new Point("Blue");
        boardPiece[0][0] = new Plus("Blue");
        boardPiece[0][6] = new Plus("Blue");
        boardPiece[0][1] = new Hourglass("Blue");
        boardPiece[0][5] = new Hourglass("Blue");
        boardPiece[0][2] = new Time("Blue");
        boardPiece[0][4] = new Time("Blue");
        boardPiece[0][3] = new Sun("Blue");
    }

    // Moves a piece from the specified start position to the target position
    public void movePiece(int startRow, int startCol, int targetRow, int targetCol) {
        Piece tempPiece = boardPiece[startRow][startCol];
        boardPiece[startRow][startCol] = null;
        boardPiece[targetRow][targetCol] = tempPiece;
    }

    // Clears the board by setting all pieces to null
    public void clearBoard() {
        for (int i = 0; i < boardPiece.length; i++) {
            for (int j = 0; j < boardPiece[i].length; j++) {
                boardPiece[i][j] = null;
            }
        }
    }

    // Flips the entire board along the vertical axis
    public void flipBoard() {
        flipped = !flipped;
        Piece[][] newBoardPiece = new Piece[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                newBoardPiece[i][j] = boardPiece[5 - i][6 - j];
            }
        }
        boardPiece = newBoardPiece;
    }

    // Swap the Plus and Time pieces on the board (exchange their roles)
    public void swapPlusTime() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Piece piece = boardPiece[i][j];
                if (piece instanceof Time) {
                    boardPiece[i][j] = new Plus(piece.pieceColor());
                } else if (piece instanceof Plus) {
                    boardPiece[i][j] = new Time(piece.pieceColor());
                }
            }
        }
    }

    // Checks if the Sun piece of the specified color is still on the board
    public boolean isSunOnBoard(String color) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Piece piece = boardPiece[i][j];
                if (piece instanceof Sun && piece.pieceColor().equals(color)) {
                    return true; // Sun is still on the board
                }
            }
        }
        return false; // Sun is not found on the board
    }
}