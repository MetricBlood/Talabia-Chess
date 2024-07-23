// Model class to represent the Point piece
public class Point extends Piece {
    
    private int direction;
    
    public Point(String color) {
        super(color);
        this.direction = -1;
    }

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board) {
        if (currentRow == 0 || currentRow == 5) {
            direction = (targetRow - currentRow) / Math.abs(targetRow - currentRow);
        }
    
        if (targetCol == currentCol && Math.abs(targetRow - currentRow) == 2) {
            if (direction == 1 && targetRow == currentRow + 2) { // Allow moving 2 steps forward only when direction is forward
                if (board.getBoardPiece()[currentRow + 1][currentCol] == null) {
                    return true;
                }
            } else if (direction == -1 && targetRow == currentRow - 2) { // Allow moving 2 steps backward only when direction is reversed
                if (board.getBoardPiece()[currentRow - 1][currentCol] == null) {
                    return true;
                }
            }
        } else if (targetCol == currentCol && targetRow == currentRow + direction) { //Allow moving 1 step
            return true;
        }
    
        return false;
    }
    
    // Add this method to get the icon name
    public String getIconName() {
        if (direction==1) {
            return pieceColor().equals("Yellow") ? "YPoint_flipped" : "BPoint_flipped";
        } else {
            return pieceColor().equals("Yellow") ? "YPoint" : "BPoint";
        }
    }
}
