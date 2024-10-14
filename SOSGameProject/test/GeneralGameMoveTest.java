import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

//User Story 6: Make a move in a general game
public class GeneralGameMoveTest {

    private SOSBoard board;

    @Before
    public void setUp() {
        // Initialize a 3x3 board for the test, in "General" game mode
        board = new SOSBoard(3, "General");
    }

    //6.1 player makes a move on empty space
    @Test
    public void testMakeMove_EmptyCell() {
        // Ensure the cell is initially empty
        assertTrue(board.isCellEmpty(1, 1));

        // Player "S" makes a move in the empty cell
        board.setCellValue(1, 1, board.getCurrentPlayer());

        // Verify the cell is now filled with "S"
        assertEquals("S", board.getCellValue(1, 1));

        // Ensure the cell is no longer empty
        assertFalse(board.isCellEmpty(1, 1));
    }

    //6.2 Making a move on filled board slot
    @Test
    public void testMakeMove_FilledCell() {
        // Player "S" makes a move in the cell
        board.setCellValue(1, 1, board.getCurrentPlayer());

        // Try to make another move in the same cell
        if (!board.isCellEmpty(1, 1)) {
            // This move should be rejected (no change)
            board.setCellValue(1, 1, board.getCurrentPlayer());
        }

        // Verify that the cell still contains the first player's move ("S")
        assertEquals("S", board.getCellValue(1, 1));
    }
}