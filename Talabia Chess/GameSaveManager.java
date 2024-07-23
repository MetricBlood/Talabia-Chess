import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

// Model class to write and access data in a txt file (use in save/load feature)
public class GameSaveManager {

    // To write data in a txt file
    public static boolean save(String filename, Piece[][] boardPiece, boolean flipped, int turn, int moveCounter) {

        try {
            // To set the txt file location
            String directory = "C:SaveFile";
            String filePath = directory + "/" + filename;

            // Create the directory and its parent directories if it doesn't exist
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Write the data into the file
            FileWriter writer = new FileWriter(filePath);

            writer.write("boardPiece: ");
            for (Piece[] row : boardPiece) {
                for (Piece piece : row) {
                    if (piece != null) {
                        writer.write(piece.getIconName() + ",");
                    } else {
                        writer.write("null,");
                    }
                }
            }
            writer.write("\n");

            writer.write("flipped: " + flipped + "\n");
            writer.write("turn: " + turn + "\n");
            writer.write("moveCounter: " + moveCounter + "\n");

            writer.close();
            return true;

        } catch (IOException e) {

            System.out.println("Failed to save the game: " + e.getMessage());
            return false;
        }
    }

    // To access the data in a txt file
    public static GameData load(String filename) {

        try {
            // To load the txt file at the location
            String directory = "C:SaveFile";
            String filePath = directory + "/" + filename;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // Variable declaration and initialisation
            String line;
            Piece[][] boardPiece = new Piece[6][7];
            boolean flipped = false;
            int turn = 0;
            int moveCounter = 0;

            // Loop while txt file has a line
            while ((line = reader.readLine()) != null) {

                // Split the line into two parts seperated by ":" into an array
                String[] parts = line.split(": ");

                // If a line in a txt file has data
                if (parts.length == 2) {
                    // Seperate the data into two parts, key and value
                    String key = parts[0];
                    String value = parts[1];

                    // Access the value of a data by their key and add it into this class variable
                    if (key.equals("boardPiece")) {
                        String[] boardPieceArray = value.split(",");
                        int row = 0;
                        int col = 0;
                        for (String piece : boardPieceArray) {
                            if (piece.equals("YPoint")) {
                                boardPiece[row][col] = new Point("Yellow");
                            } else if (piece.equals("YPlus")) {
                                boardPiece[row][col] = new Plus("Yellow");
                            } else if (piece.equals("YHourglass")) {
                                boardPiece[row][col] = new Hourglass("Yellow");
                            } else if (piece.equals("YTime")) {
                                boardPiece[row][col] = new Time("Yellow");
                            } else if (piece.equals("YSun")) {
                                boardPiece[row][col] = new Sun("Yellow");
                            }

                            if (piece.equals("BPoint")) {
                                boardPiece[row][col] = new Point("Blue");
                            } else if (piece.equals("BPlus")) {
                                boardPiece[row][col] = new Plus("Blue");
                            } else if (piece.equals("BHourglass")) {
                                boardPiece[row][col] = new Hourglass("Blue");
                            } else if (piece.equals("BTime")) {
                                boardPiece[row][col] = new Time("Blue");
                            } else if (piece.equals("BSun")) {
                                boardPiece[row][col] = new Sun("Blue");
                            }

                            col++;
                            if (col == 7) {
                                col = 0;
                                row++;
                            }
                        }

                    } else if (key.equals("flipped")) {
                        flipped = Boolean.parseBoolean(value);
                    } else if (key.equals("turn")) {
                        turn = Integer.parseInt(value);
                    } else if (key.equals("moveCounter")) {
                        moveCounter = Integer.parseInt(value);
                    }
                }
            }

            reader.close();
            return new GameData(boardPiece, flipped, turn, moveCounter);

        } catch (IOException e) {
            System.out.println("Failed to load the game: " + e.getMessage());
        }

        return null;
    }
}
