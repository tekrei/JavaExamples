package net.tekrei.xox.game;

import java.util.ArrayList;

/**
 * Oyun agacindaki dugumleri temsil eden sinif
 * 
 * @author emre
 * 
 */
public class Node {

	private byte[] board;

	private Move move = null;

	private Node[] children;

	public Node(byte[] b, Move m) {
		board = b;
		move = m;
		generateChildren();
	}

	public Move getMove() {
		return move;
	}

	public byte getPlayer() {
		return (move == null ? Board.HUMAN : Board.changePlayer(move
				.getPlayer()));
	}

	public boolean isLeaf() {
		if (children != null) {
			return (children.length == 0);
		}
		return true;
	}

	public byte[] getBoard() {
		return board;
	}

	/**
	 * Method to calculate value of this node
	 * 
	 * @return value of this node
	 */
	public byte getValue() {
		if (Board.isWinner(board, Board.COMPUTER)) {
			return Board.COMPUTER;
		} else if (Board.isWinner(board, Board.HUMAN)) {
			return Board.HUMAN;
		} else {
			return Board.EMPTY;
		}
	}

	/**
	 * Method to generate children of the node (next possible moves)
	 * 
	 */
	public void generateChildren() {
		ArrayList<Node> c = null;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Move m = new Move(x, y, this.getPlayer());
				if (Board.isEmpty(board, m)) {
					if (c == null) {
						c = new ArrayList<Node>();
					}
					byte[] newPos = Board.createCopy(board);
					Board.doMove(newPos, m);
					c.add(new Node(newPos, m));
				}
			}
		}
		if (c != null) {
			children = c.toArray(new Node[] {});
		} else {
			children = new Node[0];
		}
	}

	public Node[] getChildren() {
		return children;
	}
}