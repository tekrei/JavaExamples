package net.tekrei.xox;

import net.tekrei.xox.ui.BoardPanel;
import net.tekrei.xox.ui.LeftPanel;

import javax.swing.*;
import java.awt.*;

public class XOX extends JFrame {

    private static final long serialVersionUID = 1L;

    private BoardPanel pnlBoard = null;

    private LeftPanel pnlLeft;

    private XOX() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new XOX();
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

    private LeftPanel getPnlLeft() {
        if (pnlLeft == null) {
            pnlLeft = new LeftPanel();
            pnlLeft.setBounds(new Rectangle(0, 0, 135, 130));
        }
        return pnlLeft;
    }

    public void addScore(String winner) {
        JOptionPane.showMessageDialog(this, "Winner is " + winner);
        pnlLeft.puanEkle(winner);
    }
}
