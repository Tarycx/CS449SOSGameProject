public class GameMenu {
    private String gameType;
    private int boardSize;
    private String bluePlayerType = "Human"; //Blue Default to Human
    private String redPlayerType = "Human"; //Red Default to Human


    public GameMenu() {
        // Default settings for game type and board size
        this.gameType = "Simple Game";
        this.boardSize = 3;
    }

    //Getters and Setters for game type and board size
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        if (boardSize >= 3 && boardSize <= 10) {
            this.boardSize = boardSize;
        } else {
            throw new IllegalArgumentException("Board size must be between 3 and 10.");
        }
    }

    public void setBluePlayerType(String type) {
        this.bluePlayerType = type;
    }

    public void setRedPlayerType(String type) {
        this.redPlayerType = type;
    }

    public String getBluePlayerType() {
        return bluePlayerType;
    }

    public String getRedPlayerType() {
        return redPlayerType;
    }
     // Start the game by creating an SOSBoard and SOSGameGUI returns game mode instance
     public void startGame() {
        SOSBoard game;
        if (gameType.equals("Simple Game")) {
            game = new SOSGameSimple(boardSize, bluePlayerType, redPlayerType);//UPDATE: Pass Player Type
        } else {
            //Return an instance of SOSGameGeneral when General mode is implemented
            game = new SOSGameGeneral(boardSize, bluePlayerType, redPlayerType);//UPDATE: Pass Player Type  
        }
        new SOSGameGUI(game); //Passes the game instance to the GUI
    }
}

