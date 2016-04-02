package net.tekrei.xox;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.tekrei.xox.ui.LeftPanel;
import net.tekrei.xox.ui.BoardPanel;

public class XOX extends JFrame {

	private static final long serialVersionUID = 1L;

	private BoardPanel pnlBoard = null;

	private LeftPanel pnlLeft; // @jve:decl-index=0:

	public XOX() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(new Dimension(295, 180));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void addScore(String kazanan) {
		JOptionPane.showMessageDialog(this, "Winner is:"+kazanan);
		pnlLeft.puanEkle(kazanan);
	}

	public static void main(String[] args) {
		new XOX();
	}
} // @jve:decl-index=0:visual-constraint="10,10"
