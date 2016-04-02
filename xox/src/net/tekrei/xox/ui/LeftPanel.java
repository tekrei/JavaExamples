package net.tekrei.xox.ui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public final static String HUMAN = "Human"; // @jve:decl-index=0:

	public final static String COMPUTER = "Computer";

	private JButton btnCikis = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JLabel lblBilgisayar = null;

	private JLabel lblInsan = null;

	public LeftPanel() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		lblInsan = new JLabel();
		lblInsan.setBounds(new java.awt.Rectangle(80, 50, 51, 21));
		lblInsan.setForeground(new Color(51, 51, 255));
		lblInsan.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblInsan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInsan.setText("0");
		lblBilgisayar = new JLabel();
		lblBilgisayar.setBounds(new java.awt.Rectangle(80, 30, 51, 21));
		lblBilgisayar.setForeground(new Color(255, 51, 51));
		lblBilgisayar.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblBilgisayar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBilgisayar.setText("0");
		jLabel11 = new JLabel();
		jLabel11.setBounds(new java.awt.Rectangle(0, 50, 81, 21));
		jLabel11.setText("Ýnsan:");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new java.awt.Rectangle(0, 30, 81, 21));
		jLabel1.setText("Bilgisayar:");
		this.setSize(134, 130);
		this.setLayout(null);
		this.add(getBtnCikis(), null);
		this.add(jLabel1, null);
		this.add(jLabel11, null);
		this.add(lblBilgisayar, null);
		this.add(lblInsan, null);
	}

	private JButton getBtnCikis() {
		if (btnCikis == null) {
			btnCikis = new JButton();
			btnCikis.setBounds(new Rectangle(0, 100, 130, 21));
			btnCikis.setText("Çýkýþ");
			btnCikis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return btnCikis;
	}

	public void puanEkle(String enSonOynayan) {
		if (enSonOynayan.equals(COMPUTER)) {
			lblBilgisayar.setText(""
					+ (Integer.parseInt(lblBilgisayar.getText()) + 1));
		} else {
			lblInsan.setText("" + (Integer.parseInt(lblInsan.getText()) + 1));
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
