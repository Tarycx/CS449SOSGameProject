import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SOSBoardTest {

    private SOSBoard board;

    @Before
    public void setUp() {
        board = new SOSBoard(3, "Simple");  // Initialize a 3x3 board with "Simple" mode
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(3, board.getSize());  // Verify the size of the board is correct
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                assertNull(board.getCellValue(i, j));  // Ensure all cells are empty (null) at the start
            }
        }
    }

    @Test
    public void testSetAndGetCellValue() {
        board.setCellValue(0, 0, "S");  // Set the cell at (0,0) to "S"
        assertEquals("S", board.getCellValue(0, 0));  // Ensure the value is set correctly

        board.setCellValue(1, 1, "O");  // Set the cell at (1,1) to "O"
        assertEquals("O", board.getCellValue(1, 1));  // Ensure the value is set correctly
    }

    @Test
    public void testTogglePlayer() {
        assertEquals("S", board.getCurrentPlayer());  // Verify the initial player is "S"
        assertEquals("Blue", board.getCurrentPlayerColor());  // Verify the initial player color is Blue

        board.togglePlayer();  // Toggle to the next player
        assertEquals("O", board.getCurrentPlayer());  // Ensure it switches to "O"
        assertEquals("Red", board.getCurrentPlayerColor());  // Ensure the player color changes to Red

        board.togglePlayer();  // Toggle back to the initial player
        assertEquals("S", board.getCurrentPlayer());  // Ensure it switches back to "S"
        assertEquals("Blue", board.getCurrentPlayerColor());  // Ensure the player color changes back to Blue
    }

    @Test
    public void testIsCellEmpty() {
        assertTrue(board.isCellEmpty(0, 0));  // Initially, cell (0,0) should be empty

        board.setCellValue(0, 0, "S");  // Set cell (0,0) to "S"
        assertFalse(board.isCellEmpty(0, 0));  // Now cell (0,0) should not be empty
    }

    @Test
    public void testGetGameMode() {
        assertEquals("Simple", board.getGameMode());  // Check the game mode is set correctly
    }

    @Test
    public void testGameWonPlaceholder() {
        // Since the win logic is a placeholder, test that isGameWon() returns false by default
        assertFalse(board.isGameWon());  // Placeholder should return false
    }
}
