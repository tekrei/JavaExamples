package net.tekrei;

import javax.swing.*;
import java.awt.*;

class ViewPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblOriginal = null;

    private JLabel lblModified = null;

    ViewPanel() {
        super();
        initialize();
    }

    private void initialize() {
        JLabel jLabel21 = new JLabel("Modified Picture");
        jLabel21.setBounds(new Rectangle(310, 300, 301, 21));
        jLabel21.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel21.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel jLabel2 = new JLabel();
        jLabel2.setBounds(new Rectangle(0, 300, 301, 21));
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Original Picture");
        lblModified = new JLabel();
        lblModified.setHorizontalTextPosition(SwingConstants.CENTER);
        lblModified.setHorizontalAlignment(SwingConstants.CENTER);
        lblModified.setBounds(new Rectangle(310, 0, 301, 301));
        lblOriginal = new JLabel(new ImageIcon("net/tekrei/dip/img/rice.png"));
        lblOriginal.setHorizontalTextPosition(SwingConstants.CENTER);
        lblOriginal.setHorizontalAlignment(SwingConstants.CENTER);
        lblOriginal.setBounds(new Rectangle(0, 0, 301, 301));
        this.setSize(617, 326);
        this.setLayout(null);
        this.add(lblOriginal, null);
        this.add(lblModified, null);
        this.add(jLabel2, null);
        this.add(jLabel21, null);
    }

    void updateOriginal(Image image) {
        lblOriginal.setIcon(new ImageIcon(image));
        this.updateUI();
    }

    void updateModified(Image image) {
        lblModified.setIcon(new ImageIcon(image));
        this.updateUI();
    }
}
