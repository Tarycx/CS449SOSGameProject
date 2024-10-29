import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SOSBoardTest {

    private SOSGameSimple board;  // Updated to reflect the use of SOSGameSimple

    @Before
    public void setUp() {
        // Initialize a 3x3 board with "Simple" mode
        board = new SOSGameSimple(3);
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(3, board.getSize());  // Verify the size of the board is correct
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                assertEquals("", board.getCellValue(i, j));  // Ensure all cells are empty (empty string) at the start
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
        assertEquals("Simple", board.getGameType());  // Check the game type is correctly set to Simple
    }

    @Test
    public void testWinConditionOnFirstSOS() {
        // Create an SOS sequence to test win detection
        board.makeMove(0, 0, "S");
        board.makeMove(0, 1, "O");
        boolean isGameWon = board.makeMove(0, 2, "S");

        // Verify that the game correctly detects the win
        assertTrue(isGameWon);
        assertEquals("Blue wins!", board.getWinner());  // Assuming Blue made the winning move
    }


}
