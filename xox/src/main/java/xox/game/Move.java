package xox.game;

/**
 * Simple data structure to store move information
 */
public class Move {

    private final int row;

    private final int column;

    private final byte player;

    public Move(int r, int c, byte p) {
        super();
        this.row = r;
        this.column = c;
        this.player = p;
    }

    byte getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
