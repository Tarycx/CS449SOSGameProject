import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;

// Unit testing for game play GUI Using both JUnit(limited for testing GUIs) and AssertJ-swing libraries
public class SOSGameGUITest {

    private SOSGameGUI gameGUI;
    private SOSBoard board;

    @Before
    public void setUp() {
        // Initialize a 3x3 board for testing
        board = new SOSBoard(3, "Simple");
        gameGUI = new SOSGameGUI(board);
    }

    @Test
    public void testInitialSetup() {
        // Verify that the title is correctly set
        assertEquals("SOS Game", gameGUI.getTitle());

        // Verify the board size label
        JLabel boardSizeLabel = findLabel(gameGUI, "Board Size: 3");
        assertNotNull(boardSizeLabel);

        // Verify the buttons are initialized correctly
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = gameGUI.getButtonAt(i, j);
                assertNotNull(button);
                assertEquals("", button.getText());
            }
        }
    }

    @Test
    public void testButtonClick() {
        // Simulate a click on the first button (0,0)
        JButton button = gameGUI.getButtonAt(0, 0);
        assertNotNull(button);

        // Simulate a click on the button
        button.doClick();

        // Verify the button text has changed to the selected move, which is "S" by default
        assertEquals("S", button.getText());
    }

    @Test
    public void testPlayerTurnLabel() {
        // Initially, the turn label should indicate it's Blue's turn
        JLabel playerTurnLabel = findLabel(gameGUI, "Player Turn: Blue");
        assertNotNull(playerTurnLabel);

        // Simulate a move to toggle the turn
        JButton button = gameGUI.getButtonAt(0, 0);
        button.doClick();  // First move by Blue (default)

        // Verify the turn label has updated for the next player's turn (Red)
        playerTurnLabel = findLabel(gameGUI, "Player Turn: Red");
        assertNotNull(playerTurnLabel);
    }

    // Utility method to find a JLabel by text in a container (if not directly accessible)
    private JLabel findLabel(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().equals(text)) {
                    return label;
                }
            } else if (component instanceof Container) {
                JLabel label = findLabel((Container) component, text);
                if (label != null) {
                    return label;
                }
            }
        }
        return null;
    }
}
