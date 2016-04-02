package net.tekrei.xox.game;

/**
 * Simple data structure to store move information
 * 
 * @author emre
 * 
 */
public class Move {

	private int row;

	private int column;

	private byte player;

	public Move(int r, int c, byte p) {
		super();
		this.row = r;
		this.column = c;
		this.player = p;
	}

	public byte getPlayer() {
		return player;
	}

	public void setPlayer(byte p) {
		this.player = p;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int r) {
		this.row = r;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int c) {
		this.column = c;
	}

}
