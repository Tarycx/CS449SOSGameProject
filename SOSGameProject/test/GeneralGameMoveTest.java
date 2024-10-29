import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// User Story 6: Make a move in a general game
public class GeneralGameMoveTest {

    private SOSGameGeneral board;

    @Before
    public void setUp() {
        // Initialize a 3x3 board for the test, in "General" game mode
        board = new SOSGameGeneral(3);
    }

    // 6.1 player makes a move on an empty space
    @Test
    public void testMakeMove_EmptyCell() {
        // Ensure the cell is initially empty
        assertTrue(board.isCellEmpty(1, 1));

        // Player makes a move in the empty cell with "S"
        boolean moveResult = board.makeMove(1, 1, "S");

        // Verify the cell is now filled with "S"
        assertEquals("S", board.getCellValue(1, 1));

        // Ensure the cell is no longer empty
        assertFalse(board.isCellEmpty(1, 1));

        // Verify that the move does not end the game if it's not the last move
        assertFalse(moveResult);
    }

    // 6.2 Making a move on an already filled cell
    @Test
    public void testMakeMove_FilledCell() {
        // Player makes an initial move in the cell with "S"
        board.makeMove(1, 1, "S");

        // Try to make another move in the same cell, which should not be allowed
        boolean moveResult = board.makeMove(1, 1, "O");

        // Verify that the cell still contains the initial move ("S")
        assertEquals("S", board.getCellValue(1, 1));

        // Verify the move did not change the cell and the game did not end
        assertFalse(moveResult);
    }

    // 6.3 Player creates an SOS sequence and gets an extra turn
    @Test
    public void testMakeMove_CreatesSOS() {
        // Set up a sequence that will complete an SOS horizontally
        board.makeMove(0, 0, "S");
        board.makeMove(0, 1, "O");
        
        // Player completes the SOS by placing "S" in the next cell
        boolean moveResult = board.makeMove(0, 2, "S");

        // Verify the SOS was created
        assertTrue(board.checkHorizontalSOS(0, 1));//FixMe

        // Ensure the player gets an extra turn after creating an SOS
        assertEquals("Blue", board.getCurrentPlayerColor()); // Assuming "Blue" created the SOS

        // Verify the game has not ended since it’s the general mode
        assertFalse(moveResult);
    }

    // 6.4 Game ends when the board is full
    @Test
    public void testGameEndsWhenBoardIsFull() {
        // Fill the board without creating any SOS (draw condition)
        board.makeMove(0, 0, "S");
        board.makeMove(0, 1, "S");
        board.makeMove(0, 2, "S");
        board.makeMove(1, 0, "S");
        board.makeMove(1, 1, "S");
        board.makeMove(1, 2, "S");
        board.makeMove(2, 0, "S");
        board.makeMove(2, 1, "S");
        boolean moveResult = board.makeMove(2, 2, "S"); // Last move

        // Verify the board is full
        assertTrue(board.isBoardFull());

        // Verify the game ends
        assertTrue(moveResult);

        // Check if the game declared a draw
        assertEquals("Draw! Blue: 0 | Red: 0", board.getWinner());
    }
}
