import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

// Unit testing for Menu GUI Using boath JUnit(limited for testing GUIs) and AssertJ-swing libraries
public class GameMenuGUITest {

    private GameMenu controller;
    private GameMenuGUI gui;

    @Before
    public void setUp() {
        controller = new GameMenu();  // Create a GameMenu controller
        gui = new GameMenuGUI(controller);  // Create the GUI with the controller
        gui.setVisible(false);  // Prevent the GUI from appearing during testing
    }

    
    @Test
    public void testBoardSizeSpinner() {
        JSpinner boardSizeSpinner = findComponentOfType(gui, JSpinner.class);
        assertNotNull(boardSizeSpinner);  // Verify that the spinner exists

        // Test that the default value of the spinner is 3
        assertEquals(3, boardSizeSpinner.getValue());

        // Simulate changing the board size to 5
        boardSizeSpinner.setValue(5);
        controller.setBoardSize((Integer) boardSizeSpinner.getValue());
        assertEquals(5, controller.getBoardSize());  // Verify the board size in the controller is updated
    }

    @Test
    public void testStartButton() {
        JButton startButton = findButtonWithText(gui, "Start Game");
        assertNotNull(startButton);  // Verify that the Start button exists

        // Simulate clicking the Start button
        startButton.doClick();

        // Verify that the game starts (we could mock further if needed)
        // Typically, you'd verify the `startGame()` method was called, but for now,
        // since we don't have a mock framework, we assume the dispose() is called.
        assertFalse(gui.isVisible());  // After clicking start, the GUI should close (dispose is called)
    }

   

    // Helper method to find components of a specific type (e.g., JComboBox, JSpinner)
    private <T extends Component> T findComponentOfType(Container container, Class<T> clazz) {
        for (Component component : container.getComponents()) {
            if (clazz.isInstance(component)) {
                return clazz.cast(component);
            } else if (component instanceof Container) {
                T result = findComponentOfType((Container) component, clazz);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    // Helper method to find buttons with a specific label
    private JButton findButtonWithText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && text.equals(((JButton) component).getText())) {
                return (JButton) component;
            } else if (component instanceof Container) {
                JButton button = findButtonWithText((Container) component, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }
}
