package net.tekrei.xox.ui;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.tekrei.xox.XOX;
import net.tekrei.xox.ai.AI;
import net.tekrei.xox.game.Move;
import net.tekrei.xox.game.Board;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int next = 1;// 1 human -1 computer

	private byte[] board = new byte[9];

	private Move lastMove = null;

	private XOX mainFrame;

	private HashMap<String, JButton> buttons;

	public BoardPanel(XOX xox) {
		super();
		this.mainFrame = xox;
		initialize();
		startGame();
	}

	/**
	 * Empty the board, clear buttons and start game
	 * 
	 */
	private void startGame() {
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				board[c * 3 + r] = Board.EMPTY;
				getButton(r, c).setText("");
			}
		}
		lastMove = null;
		next = 1;
	}

	private JButton getButton(int r, int c) {
		return buttons.get(r + "" + c);
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(3);
		gridLayout.setColumns(3);
		this.setLayout(gridLayout);
		this.setBounds(new Rectangle(0, 0, 150, 150));
		// Create and add buttons
		this.add(createBtn("00"), null);
		this.add(createBtn("01"), null);
		this.add(createBtn("02"), null);
		this.add(createBtn("10"), null);
		this.add(createBtn("11"), null);
		this.add(createBtn("12"), null);
		this.add(createBtn("20"), null);
		this.add(createBtn("21"), null);
		this.add(createBtn("22"), null);
	}

	/**
	 * This method creates and returns the button with given id
	 * 
	 * @param id
	 *            id of the button to create
	 * @return created button
	 */
	private JButton createBtn(final String id) {
		JButton btn = new JButton();
		btn.setName(id);
		btn.setBorder(BorderFactory.createEtchedBorder());
		final JButton button = btn;
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClicked(button);
			}
		});
		if (buttons == null)
			buttons = new HashMap<String, JButton>();
		buttons.put(id, btn);
		return btn;
	}

	public void buttonClicked(JButton clicked) {
		String move = clicked.getName();
		int r = Integer.parseInt(move.substring(0, 1));
		int c = Integer.parseInt(move.substring(1, 2));

		doMove(r, c);
	}

	private void doMove(int r, int c) {
		if (next == 1) {
			humanMove(r, c);
		} else {
			computerMove();
		}
	}

	private void humanMove(int r, int c) {
		// perform human move
		Move h = new Move(r, c, Board.HUMAN);
		if (Board.doMove(board, h)) {
			getButton(r, c).setText("X");
			// change the player
			next = -next;
			// save last move
			lastMove = h;
			// check end game
			if (!isEndGame()) {
				// it is computers move
				doMove(0, 0);
			} else {
				//restart game
				startGame();
			}
		}
	}

	private void computerMove() {
		// Get computer move calculated using minimax
		Move h = AI.instance().getMove(board, lastMove);
		// if move is available
		if (h != null) {
			// make the move
			Board.doMove(board, h);
			getButton(h.getRow(), h.getColumn()).setText("O");
			next = -next;
		}
		if (isEndGame()) {
			startGame();
		}
	}

	private boolean isEndGame() {
		if (Board.isWinner(board, Board.HUMAN)) {
			mainFrame.addScore(LeftPanel.HUMAN);
		} else if (Board.isWinner(board, Board.COMPUTER)) {
			mainFrame.addScore(LeftPanel.COMPUTER);
		} else if (Board.isFull(board)) {
			JOptionPane.showMessageDialog(this, "Deuce!");
		} else {
			return false;
		}
		return true;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
