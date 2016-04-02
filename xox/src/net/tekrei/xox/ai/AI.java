package net.tekrei.xox.ai;

import net.tekrei.xox.game.Node;
import net.tekrei.xox.game.Move;
import net.tekrei.xox.game.Board;

public class AI {

	private static AI instance = null;

	private AI() {
	}

	public static synchronized AI instance() {
		if (instance == null) {
			instance = new AI();
		}

		return instance;
	}

	public Move getMove(byte[] board, Move previous) {

		// Create the move as Node
		Node root = new Node(board, previous);
		byte max = Byte.MIN_VALUE;

		// Node for best move
		Node bestNode = null;

		// Board is empty
		if (!Board.isFull(board)) {
			// for each children on the root
			Node[] children = root.getChildren();
			// we'll calculate minimax
			for (int i = 0; i < children.length; i++) {
				// first we must maximize
				// minimax is a recursive technique
				// we must calculate starting with max
				// followed by a min, then a max
				byte val = minimax(children[i], false);
				// If the last node value is bigger than at hand
				if (val > max) {
					max = val;
					// and take it as the best node
					bestNode = children[i];
				}
			}
			// return the move of best move found
			return bestNode.getMove();
		} else {
			// board is full!
			return null;
		}

	}

	private byte minimax(Node d, boolean min) {
		// it is a leaf, so return value directly
		if (d.isLeaf()) {
			return d.getValue();
		}

		if (min) {
			// calculate minimum
			return min(d);
		} else {
			// calculate maximum
			return max(d);
		}
	}

	private byte max(Node d) {
		byte sonuc = Byte.MIN_VALUE;
		Node[] children = d.getChildren();
		for (int i = 0; i < children.length; i++) {
			byte val = minimax(children[i], true);
			if (val > sonuc) {
				sonuc = val;
			}
		}
		return sonuc;
	}

	private byte min(Node d) {
		byte sonuc = Byte.MAX_VALUE;
		Node[] children = d.getChildren();
		for (int i = 0; i < children.length; i++) {
			byte val = minimax(children[i], false);
			if (val < sonuc) {
				sonuc = val;
			}
		}
		return sonuc;
	}
}
