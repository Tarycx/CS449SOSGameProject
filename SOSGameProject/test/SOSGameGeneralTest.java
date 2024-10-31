import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SOSGameGeneralTest {

    private SOSGameGeneral game;

    @Before
    public void setUp() {
        // Initialize a 3x3 General game board
        game = new SOSGameGeneral(3);
    }

    @Test
    public void testInitialBoardState() {
        // Verify board size is correct
        assertEquals(3, game.getSize());

        // Verify all cells are initially empty
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                assertEquals("", game.getCellValue(i, j));
            }
        }
    }

    @Test
    public void testMakeMoveOnEmptyCell() {
        // Make a move on an empty cell
        boolean gameEnded = game.makeMove(0, 0, "S");

        // Verify the cell is now occupied by "S"
        assertEquals("S", game.getCellValue(0, 0));

        // Ensure the game has not ended as General mode only ends when the board is full
        assertFalse(gameEnded);
    }

    @Test
    public void testContinuousTurnAfterSOS() {
        // Create an "SOS" sequence horizontally
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        boolean gameEnded = game.makeMove(0, 2, "S");

        // Verify the game does not end after the first SOS
        assertFalse(gameEnded);

        // Verify that the player score is updated
        assertEquals(1, game.getBlueScore());
        
        // Verify that the same player gets another turn (assuming initial player is Blue)
        assertEquals("Blue", game.getCurrentPlayerColor());
    }

    @Test
    public void testMultipleSOSScoring() {
        // Create two "SOS" sequences and verify score updates

        // First SOS sequence horizontally
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        assertEquals(1, game.getBlueScore());

        // Switch turns and create a second SOS vertically by Red
      
        game.makeMove(1, 0, "O");  // Red makes a neutral move
        game.makeMove(2, 0, "S");  // Red makes SOS
        assertEquals(1, game.getRedScore()); // Ensure Red scored
    }

    @Test
    public void testGameEndsWhenBoardIsFull() {
        // Fill the board without creating an "SOS" that ends the game prematurely
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "O");
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "O");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "O");
        boolean gameEnded = game.makeMove(2, 2, "S"); // Last move

        // Verify the board is full
        assertTrue(game.isBoardFull());

        // Verify the game ends
        assertTrue(gameEnded);

        // Check if the game declares a winner or draw based on scores
        String winnerMessage = game.getWinner();
        assertTrue(winnerMessage.contains("Wins") || winnerMessage.contains("Draw"));
    }

    @Test
    public void testResetBoard() {
        // Make some moves to set the board and scores
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");

        // Reset the board
        game.resetPlayerScores();
        game.resetSOSCellTrackers();

        //Resets board
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                game.setCellValue(i, j, "");
            }
        }

        // Verify all cells are cleared
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                assertEquals("", game.getCellValue(i, j));
            }
        }

        // Verify scores are reset to zero
        assertEquals(0, game.getBlueScore());
        assertEquals(0, game.getRedScore());
    }


    @Test
    public void testGameEndsWhenBoardIsFullWithNoWinner() {
        // Arrange: Fill the board without forming any "SOS"
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "S");
        game.makeMove(2, 2, "S");

        // Act: Check the game state
        boolean isGameOver = game.checkWinCond();
        String winner = game.getWinner();

        // Assert: Game should end with a draw
        assertTrue(!isGameOver);
        assertEquals("Draw! Blue: 0 | Red: 0", winner);
    }

    @Test
    public void testPlayerScoresMultipleSOSSequencesAndWins() {
        // Arrange: Player "Blue" creates two "SOS" sequences
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S"); // First "SOS" for Blue
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "O");
        game.makeMove(2, 2, "S"); // Second "SOS" for Blue

        // Act: Check game state after board is full
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "O"); // Filling remaining cells
        boolean isGameOver = game.checkWinCond();
        String winner = game.getWinner();

        // Assert: Game should end, and Blue should be the winner with 2 points
        assertTrue(!isGameOver);
        assertEquals(" Blue Wins! Blue Score: 2 | Red Score: 1", winner);
    }

    @Test
    public void testRedPlayerWinsWithMoreSOSSequences() {
        // Arrange: Blue and Red players create multiple "SOS" sequences, with Red scoring more
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S"); // "SOS" for Blue

        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S"); // "SOS" for Red
        game.makeMove(1, 1, "O"); // "SOS" for Red
        game.makeMove(2, 2, "O"); 
        game.makeMove(2, 1, "S");
        game.makeMove(1, 2, "S"); 

        // Act: Check game state after board is full
        boolean isGameOver = game.checkWinCond();
        String winner = game.getWinner();

        // Assert: Game should end, and Red should be the winner with a higher score: 2
        assertTrue(isGameOver);
        assertEquals(" Red Wins! Red Score: 2 | Blue Score: 1", winner);
    }

    @Test
    public void testGameEndsInDrawWithEqualSOSCounts() {
        // Arrange: Both players create one "SOS" sequence each
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S"); // "SOS" for Blue

        game.makeMove(1, 0, "O");
        game.makeMove(2, 0, "S"); // "SOS" for Red

        game.makeMove(1, 1, "S"); 

        // Fill remaining cells without creating any new "SOS" sequences
        game.makeMove(2, 0, "O");
        game.makeMove(2, 1, "O");
        game.makeMove(2, 2, "O");

        // Act: Check game state
        boolean isGameOver = game.checkWinCond();
        String winner = game.getWinner();

        // Assert: Game should end in a draw since both players scored equally
        assertTrue(!isGameOver);
        assertEquals("Draw! Blue: 1 | Red: 1", winner);
    }
}
