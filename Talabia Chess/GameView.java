import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// View class to handle the graphical user interface
public class GameView extends JFrame {

    // Variable declaration
    private GameController controller;
    private JButton[][] boardButtons;
    private JPanel buttonPanel;
    private JPanel boardPanel;

    // Path to directory containing game images
    private static final String ICONPATH = "Images/";

    // Main method to start the game
    public static void main(String[] args) {
        GameView view = new GameView();
        view.showMainMenu();
    }

    // Constructor to initialize the game
    public GameView() {
        this.controller = new GameController(this);
    }

    // To display the main menu
    private void showMainMenu() {
        getContentPane().removeAll();
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Talabia Chess");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuPanel.add(titleLabel);

        // Set the icon for the JFrame
        try {
            BufferedImage iconImage = ImageIO.read(new File(ICONPATH + "TalabiaChess.jpg"));
            setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display main menu image
        try {
            BufferedImage titleImage = ImageIO.read(new File(ICONPATH + "TalabiaChess.jpg"));
            ImageIcon titleIcon = new ImageIcon(titleImage.getScaledInstance(400, 288, Image.SCALE_SMOOTH));
            JLabel titleImageLabel = new JLabel(titleIcon);
            titleImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainMenuPanel.add(titleImageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing

        // PLAY button
        JButton playButton = new JButton("PLAY");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(e -> controller.startNewGame());
        mainMenuPanel.add(playButton);

        // LOAD button
        JButton loadButton = new JButton("LOAD");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> loadGame());
        mainMenuPanel.add(loadButton);

        // QUIT button
        JButton quitButton = new JButton("QUIT");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.addActionListener(e -> System.exit(0));
        mainMenuPanel.add(quitButton);

        // Set JFrame properties
        setTitle("Talabia Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(mainMenuPanel, BorderLayout.CENTER);
        pack();
        setSize(new Dimension(560, 480)); // Set a larger size for the main menu window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // To create button panel for the game
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> askForRestartConfirmation());
        buttonPanel.add(restartButton);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> askForSaveConfirmation());
        buttonPanel.add(saveButton);

        // Load button
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> askForLoadConfirmation());
        buttonPanel.add(loadButton);

        // Main menu button
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(e -> askForMainMenuConfirmation());
        buttonPanel.add(mainMenuButton);

        // Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> askForQuitConfirmation());
        buttonPanel.add(quitButton);
    }

    // To initialize buttons for the game board
    private void initializeButtons() {
        boardButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));

                final int finalRow = row;
                final int finalCol = col;

                // Add action listener to handle button clicks
                button.addActionListener(e -> controller.handleButtonClick(finalRow, finalCol));
                boardButtons[row][col] = button;
                boardPanel.add(button); // Add button to boardPanel
            }
        }
    }

    // To set the icon for a button
    private void setButtonIcon(JButton button, String iconName, boolean isFlipped) {
        if (iconName != null) {
            try {
                String fileName;
                // To determine whether to use flipped image for Point pieces
                if (iconName.startsWith("YPoint") || iconName.startsWith("BPoint")) {
                    // Use the flipped image for Point pieces if the board is flipped
                    fileName = isFlipped ? flipIcon(iconName) + ".png" : iconName + ".png";
                } else {
                    // Use the regular image for other pieces
                    fileName = iconName + ".png";
                }

                // Read image and set as button icon
                final BufferedImage image = ImageIO.read(new File(ICONPATH + fileName));
                ImageIcon ic = new ImageIcon(image.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                button.setIcon(ic);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            button.setIcon(null);
        }
    }

    // To flip the icon name for Point pieces
    private String flipIcon(String iconName) {
        if (iconName.startsWith("YPoint")) {
            if (iconName.equals("YPoint")) {
                iconName = "YPoint_flipped";
            } else {
                iconName = "YPoint";
            }
        } else if (iconName.startsWith("BPoint")) {
            if (iconName.equals("BPoint")) {
                iconName = "BPoint_flipped";
            } else {
                iconName = "BPoint";
            }
        }

        return iconName;
    }

    // To show the game board
    public void showGameBoard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Create button panel and add buttons at the top
        createButtonPanel();

        // Add buttons at the top
        add(buttonPanel, BorderLayout.NORTH);

        // Create board panel
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(6, 7));

        // Initialize buttons and add them to the boardPanel
        initializeButtons();

        // Add the board panel to the center
        add(boardPanel, BorderLayout.CENTER);

        pack();
        setSize(new Dimension(560, 480));
        repaint();

        Piece[][] board = controller.getBoard().getBoardPiece();
        boolean isBoardFlipped = controller.getBoard().getFlipped();

        // Update button icons based on the current state of the board
        for (int row = 0; row < controller.getBoard().getBoardPiece().length; row++) {
            for (int col = 0; col < controller.getBoard().getBoardPiece()[row].length; col++) {
                Piece piece = board[row][col];
                JButton button = boardButtons[row][col];

                if (piece != null) {
                    setButtonIcon(button, piece.getIconName(), isBoardFlipped); // Use getIconName to get the icon name
                                                                                // from the Piece
                } else {
                    setButtonIcon(button, null, false);
                }
            }
        }

        checkForWinner();
    }

    // To save the game
    private void saveGame() {
        // If save successful, display success message
        if (controller.saveGame("save.txt") == true) {
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        }
        // Otherwise, display error message
        else {
            JOptionPane.showMessageDialog(null, "Error saving game!");
        }
    }

    // To load the game
    private void loadGame() {
        // If successful, display success message
        if (controller.loadGame("save.txt") == true) {
            showGameBoard();
            JOptionPane.showMessageDialog(null, "Game loaded successfully!");
        }
        // Else, display error message
        else {
            JOptionPane.showMessageDialog(null, "Error loading game!");
        }
    }

    // To ask for confirmation before restarting the game
    private void askForRestartConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to restart the game?",
                "Restart Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.startNewGame();
        }
    }

    // To ask for confirmation before saving the game
    private void askForSaveConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to save the game?",
                "Save Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            saveGame();
        }
    }

    // To ask for confirmation before loading the game
    private void askForLoadConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to load the game?",
                "Load Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            loadGame();
        }
    }

    // To ask for confirmation before returning to main menu of the game
    private void askForMainMenuConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to go to the main menu?",
                "Main Menu Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            showMainMenu();
        }
    }

    // To ask for confirmation before quitting the game
    private void askForQuitConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Would you like to save the game before quitting?",
                "Quit Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Ask for save confirmation
            saveGame();
            System.exit(0);
        } else if (confirm == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    // To check for a winner
    private void checkForWinner() {
        String winner = null;
        if (!controller.getBoard().isSunOnBoard("Yellow")) {
            winner = "Blue";
        } else if (!controller.getBoard().isSunOnBoard("Blue")) {
            winner = "Yellow";
        }

        if (winner != null) {
            showWinner(winner);
        }
    }

    // To display the winner and result
    private void showWinner(String winnerColor) {
        Object[] options = { "Restart", "Main Menu", "Quit" };
        int choice = JOptionPane.showOptionDialog(
                null,
                winnerColor + " player wins!",
                "Game Over",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[2]);

        if (choice == 0) {
            // Restart the game
            controller.startNewGame();
        } else if (choice == 1) {
            // Exit to the main menu
            showMainMenu();
        } else if (choice == 2) {
            // Quit the game
            System.exit(0);
        }
    }
}
