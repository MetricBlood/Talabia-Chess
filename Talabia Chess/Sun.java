// Model class to represent the Sun piece
public class Sun extends Piece {

    public Sun(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board) {

        boolean x1Y1 = (Math.abs(currentCol - targetCol) <= 1) && (Math.abs(currentRow - targetRow) <= 1);

        if (x1Y1) {
            return true;
        }

        return false;
    }

    // Add this method to get the icon name
    public String getIconName() {
        return pieceColor().equals("Yellow") ? "YSun" : "BSun";
    }
}
