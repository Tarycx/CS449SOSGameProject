//Represents the board, holds the board state, and provides game logic (checking for wins).
public class SOSBoard {
    private int size;
    private String[][] board;
    private String currentPlayer;      // "S" or "O"
    private String currentPlayerColor; // "Blue" or "Red" to represent the player

    private String gameMode;           // Field for game mode

    public SOSBoard(int size, String gameMode) {
        this.size = size;
        this.board = new String[size][size];
        this.currentPlayer = "S";      // Start with player "S"
        this.currentPlayerColor = "Blue";  // Player 1 starts with Blue
        this.gameMode = gameMode;      // Store selected game mode
    }

    public int getSize() {
        return size;
    }

    public String getCellValue(int row, int col) {
        return board[row][col];
    }

    public void setCellValue(int row, int col, String value) {
        board[row][col] = value;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void togglePlayer() {
        if (currentPlayer.equals("S")) {
            currentPlayer = "O";  // Toggle to "O"
            currentPlayerColor = "Red";  // Player 2 is Red
        } else {
            currentPlayer = "S";  // Toggle to "S"
            currentPlayerColor = "Blue"; // Player 1 is Blue
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == null || board[row][col].isEmpty();
    }

    //Placeholder game logic to check for SOS, extend this to handle actual game rules
    public boolean isGameWon() {
        if (gameMode.equals("Simple")) {
            return checkSimpleWin();
        } else {
            return checkGeneralWin();
        }
    }

    //Placeholder
    private boolean checkSimpleWin() {
        // Implement simple mode win logic here
        return false; //Placeholder
    }
    //Placeholder
    private boolean checkGeneralWin() {
        // Implement general mode win logic here
        return false; //Placeholder
    }
}
