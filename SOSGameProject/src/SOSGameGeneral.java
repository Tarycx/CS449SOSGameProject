

/* General game: The game continues until the board has been filled up. The winner is the 
player who made the most SOSs. If both players made the same number of SOSs, then 
the game is a draw. When a player succeeds in creating an SOS, that player immediately 
SOS Simple game General game Board size takes another turn and continues to do so until no SOS is created on their turn. Otherwise, 
turns alternate between players after each move. 
  */


  //FixMe Digonal Line registering faluring to update on GUI board for general game
// Child Class of SOSBoard
public class SOSGameGeneral extends SOSBoard{
    private int blueScore = 0;
    private int redScore = 0;
    //private boolean[][] completeSOSCellTracker; // Track counted SOS cells FixMe: Remove
    public boolean[][] horizontalCompletedSOSTracker;
    public boolean[][] verticalCompletedSOSTracker;
    public boolean[][] leftDiagonalCompletedSOSTracker;
    public boolean[][] rightDiagonalCompletedSOSTracker;

    public SOSGameGeneral(int size) {
        super(size); //Calls constructor of superclass SOSBaord
        //completeSOSCellTracker = new boolean[size][size];
        horizontalCompletedSOSTracker = new boolean[size][size];
        verticalCompletedSOSTracker = new boolean[size][size];
        leftDiagonalCompletedSOSTracker = new boolean[size][size];
        rightDiagonalCompletedSOSTracker = new boolean[size][size];
    }

    @Override
    protected boolean checkWinCond() {
        // In the General game, we do not stop at the first SOS found
        return isBoardFull(); // The game only ends when the board is full
    }

    

    //Case: "O" overlap
    //Creaks and marks SOS sequneces (General logic) and add line data to array for painting lines, update player score
    //Checks for SOS sequence by direction and if it has already been taken. Seq Checks based on Base Class (Board logic)
    @Override
    protected boolean checkForSOS(int row, int col) {
        boolean sosFound = false;
    
        if (!horizontalCompletedSOSTracker[row][col] && checkHorizontalSOS(row, col)) {
            markCompletedSOSSequence(row, col, "horizontal");
            setPlayerScore();
            addCompletedSOS(row, col - 1, row, col + 1); //Line data

            sosFound = true;
        }
    
        if (!verticalCompletedSOSTracker[row][col] && checkVerticalSOS(row, col)) {
            markCompletedSOSSequence(row, col, "vertical");
            setPlayerScore();
            addCompletedSOS(row - 1, col, row + 1, col); //Line data

            sosFound = true;
        }
    
        if (!leftDiagonalCompletedSOSTracker[row][col] && checkLeftDiagonalSOS(row, col)) {
            markCompletedSOSSequence(row, col, "leftDiagonal");
            setPlayerScore();
            addCompletedSOS(row - 1, col - 1, row + 1, col + 1); //Line data

            sosFound = true;
        }
    
        if (!rightDiagonalCompletedSOSTracker[row][col] && checkRightDiagonalSOS(row, col)) {
            markCompletedSOSSequence(row, col, "rightDiagonal");
            setPlayerScore();
            addCompletedSOS(row - 1, col + 1, row + 1, col - 1); //Line data

            sosFound = true;
        }
    
        return sosFound; //Case: False, no SOS found
    }

    
    // Check for CASE: "O" to be resued in new "SOS"
    //Override checkForSOS to mark counted cells if an SOS is found
    private void markCompletedSOSSequence(int row, int col, String direction) {
        switch (direction) {
            case "horizontal":
                horizontalCompletedSOSTracker[row][col - 1] = true;
                horizontalCompletedSOSTracker[row][col] = true;
                horizontalCompletedSOSTracker[row][col + 1] = true;
                break;
            case "vertical":
                verticalCompletedSOSTracker[row - 1][col] = true;
                verticalCompletedSOSTracker[row][col] = true;
                verticalCompletedSOSTracker[row + 1][col] = true;
                break;
            case "leftDiagonal":
                leftDiagonalCompletedSOSTracker[row - 1][col - 1] = true;
                leftDiagonalCompletedSOSTracker[row][col] = true;
                leftDiagonalCompletedSOSTracker[row + 1][col + 1] = true;
                break;
            case "rightDiagonal":
                rightDiagonalCompletedSOSTracker[row - 1][col + 1] = true;
                rightDiagonalCompletedSOSTracker[row][col] = true;
                rightDiagonalCompletedSOSTracker[row + 1][col - 1] = true;
                break;
        }
    }

    //Cycle SOS checking
    private boolean itterateSOSChecks() {
        // Check each cell to see if an SOS pattern is created
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (checkForSOS(row, col)) {
                    return true; //Win condition met if any SOS pattern is found (First "SOS" ends Game)
                }
            }
        }
        return false; //First SOS sequence is not found Update: removel return value of isBoardFull due to True Case
    }

    
    //main logic for handling a player moves on the SOS game board and handles setting and checking win conditons and functions calls
    @Override
    public boolean makeMove(int row, int col, String value) {

        if (isCellEmpty(row, col)) {
            setCellValue(row, col, value);//setCellValue with letter("S" or "O")
            

            boolean sosCreated = itterateSOSChecks(); //SOS checking
            //Testing
            //System.out.println("Test Func: checkForSOS called with row: " + row + ", col: " + col + ", sosCreated: " + sosCreated); // Debugging line
            
            int counter = 1; // Testing counter
            //itterator for CASE: resgeristing multiple SOS Sequences completed by "S" value Ex(SO"S"OS)
            while(itterateSOSChecks()){
                counter++; // Testing counter
                //Testing Code: Case: 2 SOS sequences completed with an "S"
                System.out.println("Test Func: makeMove (General Logic) CASE: multi S itterateSOSChecks count: " + counter);
            }

            // If no SOS was created, toggle to the other player
            if (!sosCreated) {
                System.out.println("Test(General logic): sosCreateed false --> togglePlayer()"); //Testing
                togglePlayer();
            }

            // Return true if game is over (board is full), otherwise false to continue playing
            return checkWinCond(); 
        }
        return false;
    }

    @Override
    public String getWinner() {
        if (blueScore > redScore) { 
            return " Blue Wins! Blue Score: " + getBlueScore() + " | Red Score: " + getRedScore(); 
        }

        if (redScore > blueScore) {
            return " Red Wins! Red Score: " + getRedScore() + " | Blue Score: " + getBlueScore();
        }

        return "Draw! " + "Blue: " + getBlueScore()+ " | " + "Red: " + getRedScore();
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    public void setPlayerScore() {
        if (currentPlayerColor.equals("Blue")) {
            blueScore++;  // Update Blue player's score
            System.out.println("Test: Checking blueScore: " + blueScore); //Testing

        } else {
            redScore++;   // Update Red player's score
             System.out.println("Test: Checking redScore: " + redScore); //Testing
        }

    }

    @Override
    public String getGameType() {
        return "General";

     }

    //Reset player red/blue scores for a new game
    @Override
    public void resetPlayerScores() {
        redScore = 0;
        blueScore = 0;
    }

    // reset all trackers for a new game
    @Override
    public void resetSOSCellTrackers() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //completeSOSCellTracker[i][j] = false;
                horizontalCompletedSOSTracker[i][j] = false;
                verticalCompletedSOSTracker[i][j] = false;
                leftDiagonalCompletedSOSTracker[i][j] = false;
                rightDiagonalCompletedSOSTracker[i][j] = false;
            }
        }
    }


}

