package xox;

import xox.ui.BoardPanel;
import xox.ui.SidePanel;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private BoardPanel pnlBoard = null;

    private SidePanel pnlLeft;

    private Main() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void initialize() {
        this.setSize(new Dimension(295, 180));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Tic Tac Toe");
        this.getContentPane().setLayout(null);
        this.getContentPane().add(getPnlBoard());
        this.getContentPane().add(getPnlLeft());
        this.setVisible(true);
    }

    private BoardPanel getPnlBoard() {
        if (pnlBoard == null) {
            pnlBoard = new BoardPanel(this);
            pnlBoard.setBounds(new Rectangle(135, 0, 150, 150));
        }
        return pnlBoard;
    }

    private SidePanel getPnlLeft() {
        if (pnlLeft == null) {
            pnlLeft = new SidePanel();
            pnlLeft.setBounds(new Rectangle(0, 0, 135, 130));
        }
        return pnlLeft;
    }

    public void addScore(String winner) {
        JOptionPane.showMessageDialog(this, "Winner is " + winner);
        pnlLeft.addPoint(winner);
    }
}
