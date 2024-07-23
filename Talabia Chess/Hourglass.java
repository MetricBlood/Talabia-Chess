// Model class to represent the Hourglass piece
public class Hourglass extends Piece {

    public Hourglass(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, Board board) {

        boolean x2Y1 = (Math.abs(currentCol - targetCol) == 2) && (Math.abs(currentRow - targetRow) == 1);
        boolean x1Y2 = (Math.abs(currentCol - targetCol) == 1) && (Math.abs(currentRow - targetRow) == 2);

        if (x2Y1 || x1Y2) {
            return true;
        }

        return false;
    }

    public String getIconName() {
        return pieceColor().equals("Yellow") ? "YHourglass" : "BHourglass";
    }
}
