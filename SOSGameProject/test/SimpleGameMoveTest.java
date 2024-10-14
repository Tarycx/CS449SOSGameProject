import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

//User Story 4: Make a move in a Simple game
public class SimpleGameMoveTest {

    private SOSBoard board;

    @Before
    public void setUp() {
        // Initialize a 3x3 board for the test, assuming "Simple" game mode
        board = new SOSBoard(3, "Simple");
    }

    //4.1 Player makes a move on empty space
    @Test
    public void testMakeMove_EmptyCell() {
        // Ensure the cell is initially empty
        assertTrue(board.isCellEmpty(0, 0));

        // Player "S" makes a move in the empty cell
        board.setCellValue(0, 0, board.getCurrentPlayer());

        // Verify the cell is now filled with "S"
        assertEquals("S", board.getCellValue(0, 0));

        // Ensure the cell is no longer empty
        assertFalse(board.isCellEmpty(0, 0));
    }

    //4.2 Attemping to make a move in a filled cell
    @Test
    public void testMakeMove_FilledCell() {
        // Player "S" makes a move in the cell
        board.setCellValue(0, 0, board.getCurrentPlayer());

        // Attempt to make another move in the same cell
        if (!board.isCellEmpty(0, 0)) {
            // The move should not be allowed; no change should occur
            board.setCellValue(0, 0, board.getCurrentPlayer());
        }

        // Verify that the cell still contains the original player's move ("S")
        assertEquals("S", board.getCellValue(0, 0));
    }
}