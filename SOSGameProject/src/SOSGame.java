import javax.swing.SwingUtilities;

public class SOSGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMenu controller = new GameMenu();  // Initialize the controller
            new GameMenuGUI(controller);  // Launch the GUI with the controller
        });
    }
}

