
import java.util.ArrayList;
import java.util.List;

//Represents the board, holds the board state, and provides game logic (checking for wins).
public abstract class SOSBoard {
    protected int size;
    protected String[][] board;
    protected String currentPlayer;      // "S" or "O"
    protected String currentPlayerColor; // "Blue" or "Red" to represent the player

    // New field to store completed SOS sequences
    protected List<CompletedSOS> completedSOSSequences = new ArrayList<>(); //Line 

    // Inner class to store an SOS sequence
    protected static class CompletedSOS {
        int startX, startY, endX, endY;
        String color;

        public CompletedSOS(int startX, int startY, int endX, int endY, String color) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.color = color;
        }
    }

    // New method to add completed SOS
    protected void addCompletedSOS(int startX, int startY, int endX, int endY) {
        completedSOSSequences.add(new CompletedSOS(startX, startY, endX, endY, currentPlayerColor));
    }

    //remove any previously stored sequences so no lines are drawn when the board is reset
    public void resetCompletedSequences() {
        completedSOSSequences.clear(); // Clear the list of completed SOS sequences
    }





    public SOSBoard(int size) { // Update: SOSBoard parmeters changed with removal of gameType
        this.size = size;
        this.board = new String[size][size];
        //Initialize each cell to null or empty string
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ""; //each cell set to an empty string
            }
        }
        this.currentPlayer = "S";      // Start with player "S"
        this.currentPlayerColor = "Blue";  // Player 1 starts with Blue
        //this.gameMode = gameMode;      // Store selected game mode 
    }

    public int getSize() {
        return size;
    }

    public String getCellValue(int row, int col) {
        return board[row][col];
    }

    public void setCellValue(int row, int col, String value) {

        System.out.println("Test (Board Logic): Checking setCellValue Value: "
         + value 
         + " row: " + row 
         + " col: " + col
         + " player: " + getCurrentPlayerColor()); //Testing

        board[row][col] = value;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentPlayerColor() {
        return currentPlayerColor;
    }


    public void togglePlayer() { //Updated using Color over letter condition
        if (currentPlayerColor.equals("Blue")) {
            currentPlayer = "O";  // Toggle to "O"
            currentPlayerColor = "Red";  // Player 2 is Red
        } 
        else {
            currentPlayer = "S";  // Toggle to "S"
            currentPlayerColor = "Blue"; // Player 1 is Blue
        }
    }

    public boolean isCellEmpty(int row, int col) { //Function is working
        //return board[row][col] == null || board[row][col].isEmpty();
        boolean empty = board[row][col] == null || board[row][col].isEmpty();
        System.out.println("Checking if cell (" + row + ", " + col + ") is empty: " + empty); //Testing
        return empty;
        
    }

    // Cheacking if board in filled 
    public boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null || cell.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected abstract boolean checkWinCond();


    //main logic for handling a player moves on the SOS game board and handles setting and checking win conditons
    public boolean makeMove(int row, int col, String value) {
        if (isCellEmpty(row, col)) {//Testing: it never passes
            setCellValue(row, col, value);
            System.out.println("Testing Output: True isCellEmpty, SOSBoard");//Testing

            if (checkWinCond()) {
                System.out.println("Testing Output: TrueCheckWIncondition, SOSBoard");//Tesing

                return true;  // Game ends if win condition is met
            }
            togglePlayer();  // Change player after move if no win
        }

    return false;
    }


    //Checking for SOS sequence at position called each turn 
    protected boolean checkForSOS(int row, int col) {
        //Check for "SOS" pattern in all possible directions (horizontal, vertical, and diagonals)
        return checkHorizontalSOS(row, col) || 
               checkVerticalSOS(row, col) ||
               checkLeftDiagonalSOS(row, col) || 
               checkRightDiagonalSOS(row, col);
    }

    //SOS Checking methods:
    //Horizontal Check: Detects "SOS" patterns in a horizontal line.
    protected boolean checkHorizontalSOS(int row, int col) {
        if (col > 0 && col < size - 1) {
            return "S".equals(board[row][col - 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row][col + 1]);
        }
        return false;
    }
    //Vertical Check: Detects "SOS" patterns in a vertical line.
    protected boolean checkVerticalSOS(int row, int col) { 
       //Ensure we have room to check above and below

        if (row > 0 && row < size - 1) {
            return "S".equals(board[row - 1][col]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col]);
        }
        return false; 
    }

    //Diagonal Check: Detects "SOS" patterns from the top-left to bottom-right.
    protected boolean checkLeftDiagonalSOS(int row, int col) { 
        if (row > 0 && row < size - 1 && col > 0 && col < size - 1) {
            return "S".equals(board[row - 1][col - 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col + 1]);
                
        }
        return false;
    }

    //Anti-Diagonal Check: Detects "SOS" patterns from the top-right to bottom-left.
    protected boolean checkRightDiagonalSOS(int row, int col) { 
            if (row > 0 && row < size - 1 && col > 0 && col < size - 1) {
            return "S".equals(board[row - 1][col + 1]) &&
                "O".equals(board[row][col]) &&
                "S".equals(board[row + 1][col - 1]);
             
             
        }
        return false;
    }


    public String getWinner() {// deafult used by Simple Game
        return getCurrentPlayerColor() + " wins!";
    }





    public void resetPlayerScores() {
        // For General Game reset
    }

     // Helper to reset countedCells for a new game

     public void resetSOSCellTrackers() {
        // For General Game reset
     }

     public String getGameType() {
        return "Game Type(simple or General)";

     }

    // sequence object to hold information about each completed SOS sequence
    //Update for line
     public class SOSSequence {
        public int row, col;
        public String direction;

        public SOSSequence(int row, int col, String direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }

}
