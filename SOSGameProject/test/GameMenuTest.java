
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameMenuTest {

    private GameMenu gameMenu;

    @Before
    public void setUp() {
        gameMenu = new GameMenu();  // Initialize GameMenu object
    }

    // User Story 3: Start a new game of the chosen board size and game mode
    @Test
    public void testDefaultGameType() {
        assertEquals("Simple Game", gameMenu.getGameType());  // Check default game type is "Simple"
    }

    // User Story 3: Start a new game of the chosen board size and game mode
    @Test
    public void testDefaultBoardSize() {
        assertEquals(3, gameMenu.getBoardSize());  // Check default board size is 3
    }

    // User Story 3: Start a new game of the chosen board size and game mode
    @Test
    public void testStartNewGame_CustomSettings() {
        // Set custom board size and game mode
        gameMenu.setBoardSize(5);
        gameMenu.setGameType("General");

        // Start the game
        gameMenu.startGame();

       

        // Verify that the board size and game type match the chosen settings
        assertEquals(5, gameMenu.getBoardSize()); // Check that the board size is correctly set
        assertEquals("General", gameMenu.getGameType()); // Check that the game mode is correctly set
    }

    // User Story 2.2: Choose the game mode of a chosen board
    @Test
    public void testSetGameTypeGeneral() {
        gameMenu.setGameType("General");
        assertEquals("General", gameMenu.getGameType());  // Check if the game type was updated
    }

    // User Story 2.1: Choose the game mode of a chosen board
    @Test
    public void testSetGameTypeSimple() {
        gameMenu.setGameType("Simple");
        assertEquals("Simple", gameMenu.getGameType());  // Check if the game type was updated
    }

    @Test
    public void testSetValidBoardSize() {
        gameMenu.setBoardSize(5);
        assertEquals(5, gameMenu.getBoardSize());  // Check if the board size was updated
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidBoardSize() {
        gameMenu.setBoardSize(12);  // Invalid board size, should throw an exception
    }
}
