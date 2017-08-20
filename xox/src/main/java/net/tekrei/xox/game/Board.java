package net.tekrei.xox.game;

import java.util.Arrays;

/**
 * Board class containing operations
 *
 * @author emre
 */
public class Board {
    static final byte EMPTY = 0;
    public static final byte HUMAN = -1;
    public static final byte COMPUTER = 1;

    private byte[] board = new byte[9];

    public Board() {

    }

    private Board(byte[] _board) {
        board = _board;
    }

    /**
     * Checks if the move applied to an empty place
     *
     * @param move move to make
     * @return if the move is empty or not
     */
    boolean isEmpty(Move move) {
        return board[move.getRow() + (move.getColumn() * 3)] == EMPTY;
    }

    public void empty(int r, int c) {
        board[c * 3 + r] = Board.EMPTY;
    }

    /**
     * Checks if board is filled or not
     *
     * @return true if board is full
     */
    public boolean isFull() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if player is the winner in the board
     *
     * @param player player to check if it is winner on the board
     */
    public boolean isWinner(byte player) {
        // we check if player is the winner on the board
        for (int i = 0; i < 3; i++) {
            // check rows
            if (board[(i * 3)] == player && board[(1) + (i * 3)] == player && board[(2) + (i * 3)] == player) {
                return true;
            }
            // check columns
            if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
                return true;
            }
        }
        // check both diagonals
        if (board[0] == player && board[4] == player && board[8] == player) {
            return true;
        }
        if (board[2] == player && board[4] == player && board[6] == player) {
            return true;
        }
        //not winner
        return false;
    }

    /**
     * Method making the move
     *
     * @param move move to make
     */
    public boolean doMove(Move move) {
        if (board[(move.getColumn() * 3) + move.getRow()] != EMPTY) {
            return false;
        } else {
            board[move.getColumn() * 3 + move.getRow()] = move.getPlayer();
            return true;
        }
    }

    Board getClone() {
        return new Board(Arrays.copyOf(board, board.length));
    }
}
