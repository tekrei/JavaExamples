package net.tekrei;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private JSplitPane splPane = null;

    private ViewPanel pnlView = null;

    private Image imageInProcess;

    private MainWindow() {
        this("rice.png");
    }

    private MainWindow(String picture) {
        super();
        initialize(picture);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Loading " + args[0]);
            new MainWindow(args[0]);
        } else {
            new MainWindow();
        }
    }

    private void initialize(String picture) {
        this.setSize(new Dimension(800, 350));
        this.setTitle("Digital Image Processing In Java");
        this.setContentPane(getSplPane());
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loadImage(picture);
    }

    private JSplitPane getSplPane() {
        if (splPane == null) {
            splPane = new JSplitPane();
            splPane.setDividerLocation(200);
            splPane.setOneTouchExpandable(false);
            splPane.setContinuousLayout(true);
            splPane.setDividerSize(0);
            SelectPanel pnlSelect = new SelectPanel(this);
            pnlView = new ViewPanel();
            splPane.setLeftComponent(pnlSelect);
            splPane.setRightComponent(pnlView);
        }
        return splPane;
    }

    void loadImage(String path) {
        imageInProcess = Toolkit.getDefaultToolkit().getImage(path);
        pnlView.updateOriginal(imageInProcess);
    }

    void doOperation(BufferedImageOp operation) {
        BufferedImage mBufferedImage = new BufferedImage(imageInProcess
                .getWidth(null), imageInProcess.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = mBufferedImage.createGraphics();
        g2.drawImage(imageInProcess, null, null);
        pnlView.updateModified(ImageProcessing.process(mBufferedImage, operation));
    }
}
