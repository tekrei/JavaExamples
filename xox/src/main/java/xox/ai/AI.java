package xox.ai;

import xox.game.Board;
import xox.game.Move;
import xox.game.Node;

public class AI {

    public static Move getMove(Board board, Move previous) {

        // Create the move as Node
        Node root = new Node(board, previous);
        byte max = Byte.MIN_VALUE;

        // Node for best move
        Node bestNode = null;

        // Board is empty
        if (!board.isFull()) {
            // for each children on the root
            Node[] children = root.getChildren();
            // we'll calculate minimax
            for (Node aChildren : children) {
                // first we must maximize
                // minimax is a recursive technique
                // we must calculate starting with max
                // followed by a min, then a max
                byte val = minimax(aChildren, false);
                // If the last node value is bigger than at hand
                if (val > max) {
                    max = val;
                    // and take it as the best node
                    bestNode = aChildren;
                }
            }
            // return the move of best move found
            return bestNode.getMove();
        } else {
            // board is full!
            return null;
        }

    }

    private static byte minimax(Node d, boolean min) {
        // it is a leaf, so return value directly
        if (d.isLeaf()) {
            return d.getValue();
        }

        if (min) {
            // calculate minimum
            byte sonuc = Byte.MAX_VALUE;
            Node[] children = d.getChildren();
            for (Node aChildren : children) {
                byte val = minimax(aChildren, false);
                if (val < sonuc) {
                    sonuc = val;
                }
            }
            return sonuc;
        } else {
            // calculate maximum
            byte sonuc = Byte.MIN_VALUE;
            Node[] children = d.getChildren();
            for (Node aChildren : children) {
                byte val = minimax(aChildren, true);
                if (val > sonuc) {
                    sonuc = val;
                }
            }
            return sonuc;
        }
    }
}
