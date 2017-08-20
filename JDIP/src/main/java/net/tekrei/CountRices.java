package net.tekrei;

import com.jhlabs.image.MinimumFilter;
import com.jhlabs.image.ReduceNoiseFilter;
import com.jhlabs.image.ThresholdFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class CountRices extends JFrame {
    private JTextField txtThreshold = null;
    private JLabel lblOriginal = null;
    private JLabel lblAfterFiltering = null;
    private JLabel lblModified = null;
    private JLabel lblCount = null;
    private JPanel pnlView;
    private BufferedImage originalImage;

    private CountRices() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new CountRices();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 325));
        this.getContentPane().add(getPnlView(), BorderLayout.CENTER);
        lblCount = new JLabel();
        lblCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCount.setVerticalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(lblCount, BorderLayout.SOUTH);
        txtThreshold = new JTextField("173");
        txtThreshold.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    try {
                        count(Integer.parseInt(txtThreshold.getText()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.getContentPane().add(txtThreshold, BorderLayout.NORTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        Image tempImage = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("rice.png"));
        System.out.print("Loading Image");
        while (tempImage.getHeight(null) <= 0 || tempImage.getWidth(null) <= 0) {
            System.out.print(".");
        }
        System.out.println();
        lblOriginal.setIcon(new ImageIcon(tempImage));
        originalImage = new BufferedImage(tempImage.getWidth(null), tempImage.getHeight(null), BufferedImage.TYPE_USHORT_GRAY);
        Graphics2D g2 = originalImage.createGraphics();
        g2.drawImage(tempImage, null, null);
        count(173);
    }

    private void count(int threshold) {
        BufferedImage image = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_USHORT_GRAY);
        Graphics2D g2 = image.createGraphics();
        g2.drawImage(originalImage, null, null);
        image = (new MinimumFilter()).filter(image, null);
        image = (new ThresholdFilter(threshold)).filter(image, null);
        image = (new ReduceNoiseFilter()).filter(image, null);
        BufferedImage image2 = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_USHORT_GRAY);
        g2 = image2.createGraphics();
        g2.drawImage(image, null, null);
        lblAfterFiltering.setIcon(new ImageIcon(image2));
        lblCount.setText(getRiceCount(image));
        lblModified.setIcon(new ImageIcon(image));
    }

    private String getRiceCount(BufferedImage image) {
        // Nesneleri saymak lazim
        int countOfRices = 0;
        for (int row = 0; row < image.getWidth(); row++) {
            for (int column = 0; column < image.getHeight(); column++) {
                if (checkAndConvert(image, row, column)) {
                    countOfRices++;
                }
            }
        }
        return Integer.toString(countOfRices);
    }

    private boolean checkAndConvert(BufferedImage image, int row, int column) {
        if (isWhite(image, row, column)) {
            image.setRGB(row, column, 0);
            checkAndConvert(image, row + 1, column);
            checkAndConvert(image, row - 1, column);
            checkAndConvert(image, row, column + 1);
            checkAndConvert(image, row, column - 1);
            return true;
        }
        return false;
    }

    private boolean isWhite(BufferedImage image, int row, int column) {
        try {
            Color color = new Color(image.getRGB(row, column));
            if (color.equals(Color.WHITE)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;

    }

    private JPanel getPnlView() {
        if (pnlView == null) {
            pnlView = new JPanel();
            lblOriginal = new JLabel();
            lblOriginal.setHorizontalAlignment(SwingConstants.CENTER);
            lblOriginal.setVerticalAlignment(SwingConstants.CENTER);
            pnlView.add(lblOriginal);
            lblAfterFiltering = new JLabel();
            lblAfterFiltering.setHorizontalAlignment(SwingConstants.CENTER);
            lblAfterFiltering.setVerticalAlignment(SwingConstants.CENTER);
            pnlView.add(lblAfterFiltering);
            lblModified = new JLabel();
            lblModified.setHorizontalAlignment(SwingConstants.CENTER);
            lblModified.setVerticalAlignment(SwingConstants.CENTER);
            pnlView.add(lblModified);
        }
        return pnlView;
    }
}
