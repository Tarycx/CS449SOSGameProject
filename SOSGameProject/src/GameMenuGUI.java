import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GameMenuGUI extends JFrame {
    private JComboBox<String> gameTypeComboBox;
    private JSpinner boardSizeSpinner;
    private JButton startButton;
    private JButton cancelButton;

    private GameMenu controller;

    public GameMenuGUI(GameMenu controller) {
        this.controller = controller;

        // Set up the frame
        setTitle("SOS Game Setup");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel for game settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2, 2));

        // Game type selection
        JLabel gameTypeLabel = new JLabel("Select Game Type:");
        String[] gameTypes = {"Simple Game", "General Game"};
        gameTypeComboBox = new JComboBox<>(gameTypes);
        gameTypeComboBox.addActionListener(e -> controller.setGameType((String) gameTypeComboBox.getSelectedItem()));


        //Board size selection
        JLabel boardSizeLabel = new JLabel("Select Board Size:");
        boardSizeSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 10, 1));

        //Increase font size and center the value in the spinner
        JComponent editor = boardSizeSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size to 24
            textField.setHorizontalAlignment(JTextField.CENTER);  // Center the value in the input box
        }


        // Add components to settings panel
        settingsPanel.add(gameTypeLabel);
        settingsPanel.add(gameTypeComboBox);
        settingsPanel.add(boardSizeLabel);
        settingsPanel.add(boardSizeSpinner);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Start button
        startButton = new JButton("Start Game");
        startButton.addActionListener(new StartGameListener());

        // Cancel button to exit the setup menu
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));

        // Add buttons to button panel
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);

        // Add panels to the frame
        add(settingsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

        // Listener for starting the game when the Start button is pressed
    private class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve the selected board size from the spinner
            int selectedBoardSize = (int) boardSizeSpinner.getValue();

            // Set the selected board size in the controller
            controller.setBoardSize(selectedBoardSize);

            // Start the game with the updated settings
            controller.startGame();
            
            // Close the setup window after starting the game
            dispose();
        }
    }

}

