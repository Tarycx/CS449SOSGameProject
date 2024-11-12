

//Player Class with Sub Classes: Human, CPU
public abstract class Player {
    protected String color; // "Blue" or "Red"
    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // Abstract method for making a move
    public abstract boolean playerMakeMove(SOSBoard board);
}

