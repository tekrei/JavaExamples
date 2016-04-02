package net.tekrei.xox.game;

/**
 * Board class containing operations
 * 
 * @author emre
 * 
 */
public class Board {

	public static final byte EMPTY = 0;

	public static final byte HUMAN = -1;

	public static final byte COMPUTER = 1;

	/**
	 * Checks if the move applied to an empty place
	 * 
	 * @param board
	 *            board to player
	 * @param move
	 *            move to make
	 * @return if the move is empty or not
	 */
	public static boolean isEmpty(byte[] board, Move move) {
		if (board[move.getRow() + (move.getColumn() * 3)] == EMPTY) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if board is filled or not
	 * 
	 * @param board
	 *            board to check
	 * @return true if board is full
	 */
	public static boolean isFull(byte[] board) {
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
	 * @param board
	 *            current board to check
	 * @param player
	 *            player to check if it is winner on the board
	 * @return
	 */
	public static boolean isWinner(byte[] board, byte player) {
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
	 * Will change the player given as parameter
	 * 
	 * @param player
	 *            previous player
	 * @return
	 */
	public static byte changePlayer(byte player) {

		if (player == HUMAN) {
			return COMPUTER;
		} else if (player == COMPUTER) {
			return HUMAN;
		} else {
			throw new IllegalArgumentException("Only " + HUMAN + " or " + COMPUTER + " is allowed.");
		}
	}

	/**
	 * Method which creates and returns a copy of the board given as parameter
	 * 
	 * @param board
	 *            board to copy
	 * @return a copy of the board
	 */
	public static byte[] createCopy(byte[] board) {
		byte[] newBoard = new byte[9];
		for (int i = 0; i < 9; i++) {
			newBoard[i] = board[i];
		}
		return newBoard;
	}

	/**
	 * Method making the move
	 * 
	 * @param board
	 *            board
	 * @param move
	 *            move to make
	 * @return
	 */
	public static boolean doMove(byte[] board, Move move) {
		if (board[(move.getColumn() * 3) + move.getRow()] != EMPTY) {
			return false;
		} else {
			board[move.getColumn() * 3 + move.getRow()] = move.getPlayer();
			return true;
		}
	}
}
