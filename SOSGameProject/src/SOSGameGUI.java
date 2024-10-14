import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main GUI for the game.
public class SOSGameGUI extends JFrame {
    private SOSBoard board;
    private JButton[][] buttons;
    private String selectedMove = "S"; //Default move is "S"
    
    private JLabel playerTurnLabel; //Label to display current player's turn
    private JLabel boardSizeLabel;  //Label to display board size

    public SOSGameGUI(SOSBoard board) {
        this.board = board;
        this.buttons = new JButton[board.getSize()][board.getSize()];

        setTitle("SOS Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       


        // Set the layout for the main frame
        setLayout(new BorderLayout());

        // Create panel for move selection, board size, and player turn display
        JPanel moveSelectionPanel = new JPanel();
        JRadioButton sRadioButton = new JRadioButton("S", true);  // "S" is selected by default
        JRadioButton oRadioButton = new JRadioButton("O");

        // Add the radio buttons to a ButtonGroup to ensure only one can be selected at a time
        ButtonGroup group = new ButtonGroup();
        group.add(sRadioButton);
        group.add(oRadioButton);

        // Add action listeners for the radio buttons
        sRadioButton.addActionListener(e -> selectedMove = "S");
        oRadioButton.addActionListener(e -> selectedMove = "O");

        // Initialize labels for board size and player turn
        boardSizeLabel = new JLabel("Board Size: " + board.getSize());
        playerTurnLabel = new JLabel("Player Turn: Blue");
        playerTurnLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        playerTurnLabel.setForeground(Color.BLUE); // Initial color for Blue's turn

        // Add radio buttons, board size label, and player turn label to the move selection panel
        moveSelectionPanel.add(boardSizeLabel);
        moveSelectionPanel.add(playerTurnLabel);
        moveSelectionPanel.add(new JLabel("Select your move: "));
        moveSelectionPanel.add(sRadioButton);
        moveSelectionPanel.add(oRadioButton);

        // Add the "New Game" button to the move selection panel
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose of the current game window and launch the game setup menu
                dispose();  // Close the current game window
                SwingUtilities.invokeLater(() -> {
                    GameMenu controller = new GameMenu();  // Create a new game controller
                    new GameMenuGUI(controller);  // Open the game setup menu with the new controller
                });
            }
        });
        
        moveSelectionPanel.add(newGameButton);

        //Create the game board panel
        JPanel gameBoardPanel = initializeBoard();

        //Add the panels to the frame
        add(moveSelectionPanel, BorderLayout.NORTH); //Place move selection panel at the top
        add(gameBoardPanel, BorderLayout.CENTER);    //Place the game board in the center

        //Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Initialize the game board with buttons
    private JPanel initializeBoard() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(board.getSize(), board.getSize())); // Grid layout for the game board

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panel.add(buttons[i][j]);
            }
        }

        return panel;  //Return the game board panel
    }

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = buttons[row][col];

            // Ask the game logic if the cell is empty
            if (board.isCellEmpty(row, col)) {
                // Update the board model with the selected move ("S" or "O")
                board.setCellValue(row, col, selectedMove);

                // Set the background color based on the current player
                if (board.getCurrentPlayerColor().equals("Blue")) {
                    clickedButton.setBackground(Color.BLUE);
                } else {
                    clickedButton.setBackground(Color.RED);
                }

                // Update the button text with the selected move
                clickedButton.setText(selectedMove);
                clickedButton.setOpaque(true);

                // Check if the game is won after this move
                if (board.isGameWon()) {
                    JOptionPane.showMessageDialog(null, board.getCurrentPlayerColor() + " wins!");
                    resetBoard();
                } else {
                    // Toggle player for the next move
                    board.togglePlayer();

                    // Update the player turn label to show the new player's turn
                    updatePlayerTurnLabel();
                }
            }
        }
    }

    // Getter for testing methoded
    public JButton getButtonAt(int row, int col) {
        return buttons[row][col];
    }

    // Method to update the player turn label with color
    private void updatePlayerTurnLabel() {
        if (board.getCurrentPlayerColor().equals("Blue")) {
            playerTurnLabel.setText("Player Turn: Blue");
            playerTurnLabel.setForeground(Color.BLUE);  // Set text color to blue
        } else {
            playerTurnLabel.setText("Player Turn: Red");
            playerTurnLabel.setForeground(Color.RED);  // Set text color to red
        }
    }

    // Reset the game board (for restarting or when someone wins) //Originally Private changed for testing
    private void resetBoard() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.setCellValue(i, j, "");
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null); // Reset the background color
            }
        }

        // Reset the player turn label
        updatePlayerTurnLabel();
    }
}
