import javax.swing.SwingUtilities;

public class SOSGame { //Main Program launch
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMenu controller = new GameMenu();  //Initialize the controller
            new GameMenuGUI(controller);  // Launch the GUI with the controller
        });
    }
}

