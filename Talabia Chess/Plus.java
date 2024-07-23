// Model class to represent the Plus piece
public class Plus extends Piece {

    public Plus(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board) {

        boolean sameX = (currentCol == targetCol);
        boolean sameY = (currentRow == targetRow);

        if (sameX) {
            for (int i = 1; i < Math.abs(targetRow - currentRow); i++) {
                if (currentRow < targetRow && board.getBoardPiece()[currentRow + i][currentCol] != null) {
                    return false;
                } else if (currentRow > targetRow && board.getBoardPiece()[currentRow - i][currentCol] != null) {
                    return false;
                }
            }

            return true;
        }

        if (sameY) {
            for (int i = 1; i < Math.abs(targetCol - currentCol); i++) {
                if (currentCol < targetCol && board.getBoardPiece()[currentRow][currentCol + i] != null) {
                    return false;
                } else if (currentCol > targetCol && board.getBoardPiece()[currentRow][currentCol - i] != null) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    // Add this method to get the icon name
    public String getIconName() {
        return pieceColor().equals("Yellow") ? "YPlus" : "BPlus";
    }
}
