import java.util.Random;

//Computer Player with manual logic 
//Update: AI CPU intergration
//Update: Slow CPU moves down
public class CPU extends Player {
    private Random random;
    private String selectedMove = "S"; //For simplicity, CPU will always choose "S" UPDATE: add O
    public int row, col;

    public CPU(String color) {
        super(color);
        this.random = new Random();
    }

   

    @Override
    public boolean playerMakeMove(SOSBoard board) {
          
        int size = board.getSize();

         //Phase 1: potential SOS
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isCellEmpty(i, j)) {
                    // Check if "S" or "O" can complete an SOS
                    if (cpuFoundCompleteSOS(board, i, j, "S")) {
                        row = i;
                        col = j;
                        selectedMove = "S";
                        return board.makeMove(row, col, selectedMove);
                    }
                    if (cpuFoundCompleteSOS(board, i, j, "O")) {
                        row = i;
                        col = j;
                        selectedMove = "O";
                        return board.makeMove(row, col, selectedMove);
                    }
                }
            }
        }

    //Phase 2: if no SOS found then select randomly
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (!board.isCellEmpty(row, col)); // Ensure move is on an empty cell

        setRow(row);//FixMe: Replace Setters
        setCol(col);//FixMe: Replace Setters
        selectedMove = random.nextBoolean() ? "S" : "O"; // Randamly choose "S" or "O"


        return board.makeMove(row, col, selectedMove);
    }


    //CPU method to check if placing move completes a SOS
    private boolean cpuFoundCompleteSOS(SOSBoard board, int row, int col, String move) {
        // Check all directions for potential SOS sequences based on the move type
        if (move.equals("S")) {
            return cpuCheckHorizontalSOS(row, col, board, "S") ||
                    cpuCheckVerticalSOS(row, col, board, "S") ||
                    cpuCheckLeftDiagonalSOS(row, col, board, "S") ||
                    cpuCheckRightDiagonalSOS(row, col, board, "S");
        } else if (move.equals("O")) {
            return cpuCheckHorizontalSOS(row, col, board, "O") ||
                    cpuCheckVerticalSOS(row, col, board, "O") ||
                    cpuCheckLeftDiagonalSOS(row, col, board, "O") ||
                    cpuCheckRightDiagonalSOS(row, col, board, "O");
        }
        return false;
    }
        
    //CPU Checks Horizontal for SOS pattern with given move at (row, col)
    protected boolean cpuCheckHorizontalSOS(int row, int col, SOSBoard board, String move) {
        int size = board.getSize();
        if (move.equals("S")) {
            return (col > 1 && "S".equals(board.getCellValue(row, col - 2)) && "O".equals(board.getCellValue(row, col - 1))) ||
                    (col < size - 2 && "O".equals(board.getCellValue(row, col + 1)) && "S".equals(board.getCellValue(row, col + 2)));
        } else {
            return (col > 0 && col < size - 1 && "S".equals(board.getCellValue(row, col - 1)) && "S".equals(board.getCellValue(row, col + 1)));
        }
    }
    
    //CPU Checks Vertical for SOS pattern with given move at (row, col)
    protected boolean cpuCheckVerticalSOS(int row, int col, SOSBoard board, String move) {
        int size = board.getSize();
        if (move.equals("S")) {
            return (row > 1 && "S".equals(board.getCellValue(row - 2, col)) && "O".equals(board.getCellValue(row - 1, col))) ||
                    (row < size - 2 && "O".equals(board.getCellValue(row + 1, col)) && "S".equals(board.getCellValue(row + 2, col)));
        } else {
            return (row > 0 && row < size - 1 && "S".equals(board.getCellValue(row - 1, col)) && "S".equals(board.getCellValue(row + 1, col)));
        }
    }
    
    //CPU Checks Left Diagonal for SOS pattern with given move at (row, col)
    protected boolean cpuCheckLeftDiagonalSOS(int row, int col, SOSBoard board, String move) {
        int size = board.getSize();
        if (move.equals("S")) {
            return (row > 1 && col > 1 && "S".equals(board.getCellValue(row - 2, col - 2)) && "O".equals(board.getCellValue(row - 1, col - 1))) ||
                    (row < size - 2 && col < size - 2 && "O".equals(board.getCellValue(row + 1, col + 1)) && "S".equals(board.getCellValue(row + 2, col + 2)));
        } else {
            return (row > 0 && row < size - 1 && col > 0 && col < size - 1 &&
                    "S".equals(board.getCellValue(row - 1, col - 1)) && "S".equals(board.getCellValue(row + 1, col + 1)));
        }
    }
    
    //CPU Checks Right Diagonal for SOS pattern with given move at (row, col)
    protected boolean cpuCheckRightDiagonalSOS(int row, int col, SOSBoard board, String move) {
        int size = board.getSize();
        if (move.equals("S")) {
            return (row > 1 && col < size - 2 && "S".equals(board.getCellValue(row - 2, col + 2)) && "O".equals(board.getCellValue(row - 1, col + 1))) ||
                    (row < size - 2 && col > 1 && "O".equals(board.getCellValue(row + 1, col - 1)) && "S".equals(board.getCellValue(row + 2, col - 2)));
        } else {
            return (row > 0 && row < size - 1 && col > 0 && col < size - 1 &&
                    "S".equals(board.getCellValue(row - 1, col + 1)) && "S".equals(board.getCellValue(row + 1, col - 1)));
        }
    }
    public String getMoveCPU() {
        return selectedMove; //For simplicity, CPU will always choose "S" UPDATE: add O
    }

    public int getRow() {
        return row; //For simplicity, CPU will always choose "S" UPDATE: add O
    }

    public int getCol() {
        return col; //For simplicity, CPU will always choose "S" UPDATE: add O
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

}
