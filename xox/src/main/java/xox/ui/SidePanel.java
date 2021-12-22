package xox.ui;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

    final static String HUMAN = "Human"; // @jve:decl-index=0:
    final static String COMPUTER = "Computer";
    private static final long serialVersionUID = 1L;
    private JButton btnExit = null;

    private JLabel lblComputerPoint = null;

    private JLabel lblHumanPoint = null;

    public SidePanel() {
        super();
        initialize();
    }

    private void initialize() {
        this.setLayout(null);
        lblHumanPoint = new JLabel();
        lblHumanPoint.setBounds(new java.awt.Rectangle(80, 50, 51, 21));
        lblHumanPoint.setForeground(new Color(51, 51, 255));
        lblHumanPoint.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblHumanPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHumanPoint.setText("0");
        lblComputerPoint = new JLabel();
        lblComputerPoint.setBounds(new java.awt.Rectangle(80, 30, 51, 21));
        lblComputerPoint.setForeground(new Color(255, 51, 51));
        lblComputerPoint.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblComputerPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        lblComputerPoint.setText("0");
        JLabel lblHuman = new JLabel();
        lblHuman.setBounds(new java.awt.Rectangle(0, 50, 81, 21));
        lblHuman.setText("Human:");
        JLabel lblComputer = new JLabel();
        lblComputer.setBounds(new java.awt.Rectangle(0, 30, 81, 21));
        lblComputer.setText("Computer:");
        this.setSize(134, 130);
        this.setLayout(null);
        this.add(getBtnExit(), null);
        this.add(lblComputer, null);
        this.add(lblHuman, null);
        this.add(lblComputerPoint, null);
        this.add(lblHumanPoint, null);
    }

    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setBounds(new Rectangle(0, 100, 130, 21));
            btnExit.setText("Exit");
            btnExit.addActionListener(e -> System.exit(0));
        }
        return btnExit;
    }

    public void addPoint(String player) {
        if (player.equals(COMPUTER)) {
            lblComputerPoint.setText(""
                    + (Integer.parseInt(lblComputerPoint.getText()) + 1));
        } else {
            lblHumanPoint.setText("" + (Integer.parseInt(lblHumanPoint.getText()) + 1));
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
