// Model class to represent the Time piece
public class Time extends Piece{
    
    public Time(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board) {
        
        boolean xY = Math.abs(currentCol-targetCol) == Math.abs(currentRow-targetRow);
        
        if (xY) {
            for (int i=1; i<Math.abs(targetRow-currentRow); i++) {
                if (currentRow<targetRow && currentCol<targetCol && board.getBoardPiece()[currentRow+i][currentCol+i]!=null) {
                    return false;
                }
                else if (currentRow<targetRow && currentCol>targetCol && board.getBoardPiece()[currentRow+i][currentCol-i]!=null) {
                    return false;
                }
                else if (currentRow>targetRow && currentCol<targetCol && board.getBoardPiece()[currentRow-i][currentCol+i]!=null) {
                    return false;
                }
                else if (currentRow>targetRow && currentCol>targetCol && board.getBoardPiece()[currentRow-i][currentCol-i]!=null) {
                    return false;
                }
            }

            return true;
        }
        
        return false;
    }
    
    // Add this method to get the icon name
    public String getIconName() {
        return pieceColor().equals("Yellow") ? "YTime" : "BTime";
    }
}
