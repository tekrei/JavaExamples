package xox.ui;

import xox.Main;
import xox.ai.AI;
import xox.game.Board;
import xox.game.Move;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BoardPanel extends JPanel {
    private final Main mainFrame;
    private final Board board = new Board();
    private Move lastMove = null;
    private HashMap<String, JButton> buttons;

    public BoardPanel(Main xox) {
        super();
        this.mainFrame = xox;
        initialize();
        startGame();
    }

    /**
     * Empty the board, clear buttons and start game
     */
    private void startGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board.empty(r, c);
                getButton(r, c).setText("");
            }
        }
        lastMove = null;
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
     * @param id id of the button to create
     * @return created button
     */
    private JButton createBtn(final String id) {
        JButton btn = new JButton();
        btn.setName(id);
        btn.setBorder(BorderFactory.createEtchedBorder());
        final JButton button = btn;
        btn.addActionListener(e -> buttonClicked(button));
        if (buttons == null)
            buttons = new HashMap<>();
        buttons.put(id, btn);
        return btn;
    }

    private void buttonClicked(JButton clicked) {
        String move = clicked.getName();
        int r = Integer.parseInt(move.substring(0, 1));
        int c = Integer.parseInt(move.substring(1, 2));

        humanMove(r, c);
    }

    private void humanMove(int r, int c) {
        // perform human move
        Move h = new Move(r, c, Board.HUMAN);
        if (board.doMove(h)) {
            getButton(r, c).setText("X");
            // save last move
            lastMove = h;
            // check end game
            if (!isEndGame()) {
                // it is computers move
                computerMove();
            } else {
                //restart game
                startGame();
            }
        }
    }

    private void computerMove() {
        // Get computer move calculated using minimax
        Move h = AI.getMove(board, lastMove);
        // if move is available
        if (h != null) {
            // make the move
            board.doMove(h);
            getButton(h.getRow(), h.getColumn()).setText("O");
        }
        if (isEndGame()) {
            startGame();
        }
    }

    private boolean isEndGame() {
        if (board.isWinner(Board.HUMAN)) {
            mainFrame.addScore(SidePanel.HUMAN);
        } else if (board.isWinner(Board.COMPUTER)) {
            mainFrame.addScore(SidePanel.COMPUTER);
        } else if (board.isFull()) {
            JOptionPane.showMessageDialog(this, "Deuce!");
        } else {
            return false;
        }
        return true;
    }
}
