package net.tekrei.xox.game;

import java.util.ArrayList;

/**
 * Oyun agacindaki dugumleri temsil eden sinif
 *
 * @author emre
 */
public class Node {

    private final Board board;

    private Move move = null;

    private Node[] children;

    public Node(Board b, Move m) {
        board = b;
        move = m;
        generateChildren();
    }

    /**
     * Will change the player given as parameter
     *
     * @param player previous player
     */
    private static byte changePlayer(byte player) {

        if (player == Board.HUMAN) {
            return Board.COMPUTER;
        } else if (player == Board.COMPUTER) {
            return Board.HUMAN;
        } else {
            throw new IllegalArgumentException("Only " + Board.HUMAN + " or " + Board.COMPUTER + " is allowed.");
        }
    }

    public Move getMove() {
        return move;
    }

    private byte getPlayer() {
        return (move == null ? Board.HUMAN : changePlayer(move.getPlayer()));
    }

    public boolean isLeaf() {
        return children == null || (children.length == 0);
    }

    /**
     * Method to calculate value of this node
     *
     * @return value of this node
     */
    public byte getValue() {
        if (board.isWinner(Board.COMPUTER)) {
            return Board.COMPUTER;
        } else if (board.isWinner(Board.HUMAN)) {
            return Board.HUMAN;
        } else {
            return Board.EMPTY;
        }
    }

    /**
     * Method to generate children of the node (next possible moves)
     */
    private void generateChildren() {
        ArrayList<Node> c = null;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Move m = new Move(x, y, this.getPlayer());
                if (board.isEmpty(m)) {
                    if (c == null) {
                        c = new ArrayList<>();
                    }
                    Board newPos = board.getClone();
                    newPos.doMove(m);
                    c.add(new Node(newPos, m));
                }
            }
        }
        if (c != null) {
            children = c.toArray(new Node[]{});
        } else {
            children = new Node[0];
        }
    }

    public Node[] getChildren() {
        return children;
    }
}