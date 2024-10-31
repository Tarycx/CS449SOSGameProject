import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Main GUI for the game.
public class SOSGameGUI extends JFrame {
    private SOSBoard board; // Reference to Board and game mode (Simple/General)
    private JButton[][] buttons;
    private String selectedMove = "S"; // Default move is "S"
    private JLabel playerTurnLabel; // Label to display current player’s turn
    private JLabel boardSizeLabel; // Label to display board size

    // Getter for the buttons array Used for testing
    public JButton[][] getButtons() {
        return buttons;
    }

    // Getter for the player turn label Used for testing
    public JLabel getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    

    public SOSGameGUI(SOSBoard board) {
        this.board = board;
        this.buttons = new JButton[board.getSize()][board.getSize()];

        setTitle("SOS Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Create a layered pane to hold buttons and lines separately
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));

        //Panel for move selection, board size, and player turn display
        JPanel moveSelectionPanel = createMoveSelectionPanel();
        add(moveSelectionPanel, BorderLayout.NORTH); //Place move selection panel at the top

        // Initialize board panel and add to lower layer
        JPanel gameBoardPanel = initializeBoard();
        gameBoardPanel.setBounds(0, 0, 500, 500);
        layeredPane.add(gameBoardPanel, JLayeredPane.DEFAULT_LAYER);

        //Transparent panel for drawing SOS lines, added above button layer
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSOSLines((Graphics2D) g); // Draw SOS lines on this layer
            }
        };
        linePanel.setOpaque(false); //Make the line panel transparent
        linePanel.setBounds(0, 0, 600, 600);
        layeredPane.add(linePanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER); // Add layeredPane to the main frame
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // Panel for move selection and game controls
    private JPanel createMoveSelectionPanel() {
        JPanel panel = new JPanel();
        JRadioButton sRadioButton = new JRadioButton("S", true);
        JRadioButton oRadioButton = new JRadioButton("O");

        // Group radio buttons to allow only one selection
        ButtonGroup group = new ButtonGroup();
        group.add(sRadioButton);
        group.add(oRadioButton);

        // Set selected move based on radio button selection
        sRadioButton.addActionListener(e -> selectedMove = "S");
        oRadioButton.addActionListener(e -> selectedMove = "O");

        // Initialize labels for board size and player turn
        boardSizeLabel = new JLabel("Board Size: " + board.getSize());
        playerTurnLabel = new JLabel("Player Turn: Blue");
        playerTurnLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        playerTurnLabel.setForeground(Color.BLUE);

        // Add components to the panel
        panel.add(boardSizeLabel);
        panel.add(playerTurnLabel);
        panel.add(new JLabel("Select your move: "));
        panel.add(sRadioButton);
        panel.add(oRadioButton);

        // New Game button to reset the game
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            dispose(); // Close current game window
            SwingUtilities.invokeLater(() -> {
                GameMenu controller = new GameMenu(); // Create new game controller
                new GameMenuGUI(controller); // Open game setup menu
            });
        });
        panel.add(newGameButton);

        return panel;
    }

    //Initializes the game board with buttons
    private JPanel initializeBoard() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(board.getSize(), board.getSize()));

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[i][j].setOpaque(false); // Make buttons transparent
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panel.add(buttons[i][j]);
            }
        }

        return panel;
    }

    //Draw SOS lines on the overlay panel
    private void drawSOSLines(Graphics2D g2d) {
        for (SOSBoard.CompletedSOS sos : board.completedSOSSequences) {
            // Set color based on the player who completed the SOS
            if ("Blue".equals(sos.color)) {
                g2d.setColor(Color.BLUE);
            } else if ("Red".equals(sos.color)) {
                g2d.setColor(Color.RED);
            }

            //Calculate pixel coordinates based on button positions (for interations to update all lines on UI)
            
            int x1 = buttons[sos.x1][sos.y1].getX() + buttons[sos.x1][sos.y1].getWidth() / 2;
            int y1 = buttons[sos.x1][sos.y1].getY() + buttons[sos.x1][sos.y1].getHeight() / 2;
            int x2 = buttons[sos.x2][sos.y2].getX() + buttons[sos.x2][sos.y2].getWidth() / 2;
            int y2 = buttons[sos.x2][sos.y2].getY() + buttons[sos.x2][sos.y2].getHeight() / 2;
            
            //Draw the line from start to end point (x1, y1) : Starting Position, (x2, y2) : Ending Position
            g2d.drawLine(x1, y1, x2, y2);
            
        }
    }

    //Button click listener to handle cell selection
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

            // Check if the cell is empty
            if (board.isCellEmpty(row, col)) {
                // Set the text color based on the current player
                if (board.getCurrentPlayerColor().equals("Blue")) {
                    clickedButton.setForeground(Color.BLUE);
                } else {
                    clickedButton.setForeground(Color.RED);
                }

                // Set the button text with the selected move
                clickedButton.setText(selectedMove);
                clickedButton.setOpaque(true);

                // Check if a winning move was made
                boolean move = board.makeMove(row, col, selectedMove);
                repaint(); // Repaint to draw lines

                if (move) {
                    JOptionPane.showMessageDialog(null, board.getWinner());
                    resetBoard();
                } else if (board.isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "The game is a draw!");
                    resetBoard();
                } else {
                    updatePlayerTurnLabel();
                }
            }
        }
    }

    // Updates player turn label with color
    private void updatePlayerTurnLabel() {
        if (board.getCurrentPlayerColor().equals("Blue")) {
            playerTurnLabel.setText("Player Turn: Blue");
            playerTurnLabel.setForeground(Color.BLUE);
        } else {
            playerTurnLabel.setText("Player Turn: Red");
            playerTurnLabel.setForeground(Color.RED);
        }
    }

    //Resets the game board for new game
    private void resetBoard() {
        board.resetCompletedSequences(); //Clear the list of completed SOS sequences 
        board.resetSOSCellTrackers(); //Tracked cells
        board.resetPlayerScores(); //Player scores

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.setCellValue(i, j, "");
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
            }
        }
        updatePlayerTurnLabel();
    }

    
}
