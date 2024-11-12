import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SOSGameGUITest {

    private SOSGameGUI gameGUI;
    private SOSGameSimple board;

    @Before
    public void setUp() {
        // Initialize the board and GUI for the "Simple" game mode
        board  = new SOSGameSimple(3, "Human", "Human");
        gameGUI = new SOSGameGUI(board);
    }

    @Test
    public void testGUIInitialization() {
        // Verify the frame title
        assertEquals("SOS Game", gameGUI.getTitle());

        // Verify the board size
        assertEquals(3, board.getSize());
        
        // Check that the game board is populated with buttons
        JButton[][] buttons = gameGUI.getButtons();
        assertEquals(3, buttons.length);
        assertEquals(3, buttons[0].length);

        // Verify each button in the grid is initially empty
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                assertEquals("", buttons[i][j].getText());
            }
        }
    }

    @Test
    public void testPlayerMoveUpdatesBoard() {
        // Simulate a player move in cell (0, 0)
        JButton[][] buttons = gameGUI.getButtons();
        JButton cellButton = buttons[0][0];
        cellButton.doClick();  // Simulates a button click
        
        // Verify the cell is updated with the current player's move ("S")
        assertEquals("S", cellButton.getText());
        
        // Verify that the board model is also updated
        assertEquals("S", board.getCellValue(0, 0));
    }

    @Test
    public void testPlayerTurnLabelUpdates() {
        // Initially, the player turn label should show "Blue"
        JLabel playerTurnLabel = gameGUI.getPlayerTurnLabel();
        assertEquals("Player Turn: Blue", playerTurnLabel.getText());
        
        // Simulate a move and verify that the player turn label updates
        gameGUI.getButtons()[0][0].doClick();  // Blue plays
        assertEquals("Player Turn: Red", playerTurnLabel.getText());

        gameGUI.getButtons()[0][1].doClick();  // Red plays
        assertEquals("Player Turn: Blue", playerTurnLabel.getText());
    }
 
}
