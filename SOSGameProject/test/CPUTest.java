import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CPUTest {
    private CPU cpuPlayer;
    private GameMenu gameMenu;
    private SOSGameSimple simpleGame;
    private SOSGameGeneral generalGame;

    @Before
    public void setUp() {
        cpuPlayer = new CPU("blue"); 
        gameMenu = new GameMenu();
        simpleGame = new SOSGameSimple(3, "CPU", "Human"); //Setup  Simple Game
        generalGame = new SOSGameGeneral(3, "CPU", "Human"); //Setup for General Game
    }

    @Test
    public void testComputerPlayerSelection() {
        /*
        AC 8.1 <scenario description> Computer Player Selection
        Given player is on game setup menu
        When they choose “CPU” as their opponent 
        Then player will play against a computer opponent 
        */ 
        gameMenu.setBluePlayerType("CPU");
        assertEquals("CPU", gameMenu.getBluePlayerType());//Blue player CPU
    }

    @Test
    public void testTurnSwitchingBetweenPlayers() {
        /*
        AC 8.2 <scenario description> Turn Switching Between Players
        Given player has finished their move
        When the game switches to computer opponent’s turn
        Then computer opponent will make its move 
        */ 
        simpleGame.makeMove( 0, 0, "S"); // Player makes a move

        
        
        boolean cpuMoveMade = cpuPlayer.playerMakeMove(simpleGame); 
        assertTrue("CPU should make a move on its turn", !cpuMoveMade);
        assertFalse("Cell chosen by CPU should not be empty", simpleGame.isCellEmpty(cpuPlayer.row, cpuPlayer.col));
    }

    @Test
    public void testCPUSequenceCompletionSimple() {
        /*
        AC 8.3 <scenario description>CPU Sequence completion Simple 
        Given a computer opponent is playing on a simple game
        When computer places a move completing an “SOS” sequence
        Then computer opponent wins game

        */ 
        
        // Set up a nearly complete SOS for the CPU to win
        simpleGame.makeMove(0, 0, "S");
        simpleGame.makeMove(0, 1, "O");
        cpuPlayer.playerMakeMove(simpleGame); // CPU completes the SOS
        
        assertTrue("CPU should win game completing SOS", simpleGame.checkWinCond());
    }

    @Test
    public void testCPUSequenceCompletionGeneral() {
        /*
        AC 8.4 <scenario description> CPU Sequence Completion General
        Given a computer opponent is playing on a general game
        When computer places move completing an "SOS" sequence,
        Then computer opponent is given a point
        */ 

        // Set up a nearly complete SOS for CPU to gain a point
        generalGame.makeMove(1, 0, "S");
        generalGame.makeMove(1, 1, "O");
        cpuPlayer.playerMakeMove(generalGame); // CPU completes SOS in general mode
        
        assertEquals("CPU should have scored a point", 1, generalGame.getBlueScore());
    }


    @Test
    public void testCPUIdentifiesSequence() {
        /*
        AC 8.5<scenario description> CPU Identifies Sequence
        Given computer opponent is playing on its turn in either the Simple or General game mode
        When there is a possible sequence “SOS” on the board that can be completed with the placement of “S” or “O”
        Then computer opponent should be able to recognize and complete sequence 
        */


         // "SO_" in Simple Game
         simpleGame.makeMove(0, 0, "S");
         simpleGame.makeMove(0, 1, "O");
 
         // Let the CPU take its turn, expected to complete the "SOS"
         boolean moveMadeSim = cpuPlayer.playerMakeMove(simpleGame);
 
         // Assertions
         assertTrue("CPU should recognize and complete the SOS sequence", moveMadeSim);
         assertEquals("S", simpleGame.getCellValue(0, 2)); // CPU should place "S" at (0, 2) to complete SOS
         assertTrue("CPU should win the game by completing SOS", simpleGame.checkWinCond());

        // "S_S" in General Game
        generalGame.makeMove(1, 0, "S");
        generalGame.makeMove(1, 2, "S");

        // Let the CPU take its turn, expected to complete the "SOS"
        boolean moveMadeGen = cpuPlayer.playerMakeMove(generalGame); //Win condtion does not output to user until board is filled

        // Assertions
        assertTrue("CPU should recognize and complete the SOS sequence", !moveMadeGen);
        assertEquals("O", generalGame.getCellValue(1, 1)); // CPU should place "S" at (1, 2) to complete SOS
        assertEquals("CPU should have scored a point", 1, generalGame.getBlueScore());
    }


}
