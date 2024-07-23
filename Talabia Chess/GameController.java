// Controller class to manage user input and updates Model and View class
public class GameController {

    // Variable declaration
    private Board board;
    private GameView view;
    private int turn = 1; // Counter to keep track of player turn
    private int selectedRow = -1;
    private int selectedCol = -1;
    private int moveCounter = 0; // Counter to keep track of number of moves

    // Constructor initializes the GameController
    public GameController(GameView view) {
        this.view = view;
        this.board = new Board();
    }

    // Getter for the board
    public Board getBoard() {
        return board;
    }

    // Starts a new game
    public void startNewGame() {
        board.clearBoard();
        board.flipped = false;
        board.initializeBoard();
        view.showGameBoard();
        turn = 1;
        moveCounter = 0;
    }

    // Handles button clicks on the game board
    public void handleButtonClick(int row, int col) {
        Piece clickedPiece = board.getBoardPiece()[row][col];

        // Based on player turns, the player can only click their own piece based on
        // color. The first click determine the piece selected.
        if ((turn % 2 == 1 && clickedPiece != null && clickedPiece.pieceColor().equals("Yellow"))
                || (turn % 2 == 0 && clickedPiece != null && clickedPiece.pieceColor().equals("Blue"))) {
            // Select the piece
            selectedRow = row;
            selectedCol = col;

        }
        // Then the player will click the targeted position to move the piece
        else if (selectedRow != -1 && selectedCol != -1) {
            // Move the selected piece
            Piece selectedPiece = board.getBoardPiece()[selectedRow][selectedCol];
            boolean validMove = false;

            if (selectedPiece instanceof Point) {
                Point point = (Point) selectedPiece;
                validMove = point.isValidMove(selectedRow, selectedCol, row, col, board);
            } else if (selectedPiece instanceof Hourglass) {
                Hourglass hourglass = (Hourglass) selectedPiece;
                validMove = hourglass.isValidMove(selectedRow, selectedCol, row, col, board);
            } else if (selectedPiece instanceof Sun) {
                Sun sun = (Sun) selectedPiece;
                validMove = sun.isValidMove(selectedRow, selectedCol, row, col, board);
            } else if (selectedPiece instanceof Plus) {
                Plus plus = (Plus) selectedPiece;
                validMove = plus.isValidMove(selectedRow, selectedCol, row, col, board);
            } else if (selectedPiece instanceof Time) {
                Time time = (Time) selectedPiece;
                validMove = time.isValidMove(selectedRow, selectedCol, row, col, board);
            }

            // Flip the board and switch turns
            if (validMove) {
                board.movePiece(selectedRow, selectedCol, row, col);
                board.flipBoard();
                moveCounter++;
                turn++;
            }

            // Check if it's time to perform the Plus and Time piece swap
            if (moveCounter >= 4) {
                board.swapPlusTime();
                moveCounter = 0; // Reset move counter after the swap
            }

            // Reset the selection
            selectedRow = -1;
            selectedCol = -1;

            // Update the view
            view.showGameBoard();
        }
    }

    // Save the game state to a file
    public boolean saveGame(String fileName) {
        // return true if successfully saved the game
        if (GameSaveManager.save(fileName, this.board.getBoardPiece(), this.board.getFlipped(), this.turn,
                this.moveCounter) == true) {
            return true;
        }

        return false;
    }

    // Load the game state from a file
    public boolean loadGame(String fileName) {
        GameData savedData = GameSaveManager.load(fileName);

        // return true if successfully load the game
        if (savedData != null) {
            board.setBoardPiece(savedData.getBoardPiece());
            board.setFlipped(savedData.getFlipped());
            this.turn = savedData.getTurn();
            this.moveCounter = savedData.getMoveCounter();
            return true;
        }

        return false;
    }

}