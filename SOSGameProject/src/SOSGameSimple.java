
/* Simple game: The player who creates the first SOS is the winner. If no SOS is created 
when the board has been filled up, then the game is a draw. Turns alternate between 
players after each move. */

//child class of SOSBoard
public class SOSGameSimple extends SOSBoard {

    public SOSGameSimple(int size) {
        super(size);
    }

    //For checking Game Type
    @Override
    public String getGameType() {
        return "Simple";

     }

    @Override
    protected boolean checkWinCond() {
        // Check each cell to see if an SOS pattern is created
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (checkForSOS(row, col)) {
                    //
                    return true; //Win condition met if any SOS pattern is found (First "SOS" ends Game)
                }
            }
        }
        return false; //First SOS sequence is not found Update: removel return value of isBoardFull due to True Case
    }


    //main logic for handling a player moves on the SOS game board and handles setting and checking win conditons
    @Override //Update to protected 
    public boolean makeMove(int row, int col, String value) {
        if (super.makeMove(row, col, value)) {
            System.out.println("Game over! Winner: " + getCurrentPlayerColor());
            return true;  // End game after winning move
        }
        
        System.out.println("Testing Output in makeMove simpleGame");
        return false;
    }



    //Checking for SOS sequence at position called each turn 
    @Override
    protected boolean checkForSOS(int row, int col) {
        //Check for "SOS" pattern in all possible directions (horizontal, vertical, and diagonals)
        return checkHorizontalSOS(row, col) || 
               checkVerticalSOS(row, col) ||
               checkLeftDiagonalSOS(row, col) || 
               checkRightDiagonalSOS(row, col);
    }

    //SOS Checking methods:
    //Horizontal Check: Detects "SOS" patterns in a horizontal line.
    @Override
    protected boolean checkHorizontalSOS(int row, int col) {
        if (col > 0 && col < size - 1) {
            if ("S".equals(board[row][col - 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row][col + 1])) {
                // Store start and end coordinates
                addCompletedSOS(row, col - 1, row, col + 1);
                return true;
            }
        }
        return false;
    }
    //Vertical Check: Detects "SOS" patterns in a vertical line.
    @Override
    protected boolean checkVerticalSOS(int row, int col) { 
       //Ensure we have room to check above and below

        if (row > 0 && row < size - 1) {
            if ("S".equals(board[row - 1][col]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col])) {
                // Store start and end coordinates
                addCompletedSOS(row - 1, col, row + 1, col);
                return true;
            }
        }
        return false; 
    }

    //Diagonal Check: Detects "SOS" patterns from the top-left to bottom-right.
    @Override
    protected boolean checkLeftDiagonalSOS(int row, int col) { 
        if (row > 0 && row < size - 1 && col > 0 && col < size - 1) {
            if ("S".equals(board[row - 1][col - 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col + 1])) {
                // Store start and end coordinates
                addCompletedSOS(row - 1, col - 1, row + 1, col + 1);
                return true;
            }
        }
        return false;
    }

    //Anti-Diagonal Check: Detects "SOS" patterns from the top-right to bottom-left.
    @Override
    protected boolean checkRightDiagonalSOS(int row, int col) { 
            if (row > 0 && row < size - 1 && col > 0 && col < size - 1) {
            if ("S".equals(board[row - 1][col + 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col - 1])) {
                // Store start and end coordinates
                addCompletedSOS(row - 1, col + 1, row + 1, col - 1);
                return true;
            }
        }
        return false;
    }
    
    
}
