package net.tekrei;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImageOp;

class SelectPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final MainWindow mainWindow;
    private JButton btnFilter = null;
    private JComboBox<BufferedImageOp> cmbFilters;
    private JButton btnLoadFile = null;

    private JFileChooser fileChooser = null;

    private JButton btnExit = null;

    private JComboBox<BufferedImageOp> cmbMorph;

    private JButton btnMorph = null;

    private JComboBox<BufferedImageOp> cmbTransform;

    private JButton btnTransform = null;

    SelectPanel(MainWindow _mainWindow) {
        super();
        mainWindow = _mainWindow;
        initialize();
    }

    private void initialize() {
        this.setSize(200, 308);
        this.setLayout(null);
        this.add(getCmbFilters());
        this.add(getBtnFilter());
        this.add(getBtnLoadFile(), null);
        this.add(getBtnExit(), null);
        this.add(getCmbMorph(), null);
        this.add(getBtnMorph(), null);
        this.add(getCmbTransform(), null);
        this.add(getBtnTransform(), null);
    }

    private JButton getBtnFilter() {
        if (btnFilter == null) {
            btnFilter = new JButton();
            btnFilter.setText("Apply Filter");
            btnFilter.setBounds(0, 50, 191, 21);
            btnFilter.setMnemonic('f');
            btnFilter.addActionListener(e -> mainWindow.doOperation(cmbFilters.getItemAt(cmbFilters.getSelectedIndex())));
        }
        return btnFilter;
    }

    private JComboBox<BufferedImageOp> getCmbFilters() {
        if (cmbFilters == null) {
            cmbFilters = new JComboBox<>(ImageProcessing.getFilters());

            cmbFilters.setBounds(0, 30, 191, 21);
        }
        return cmbFilters;
    }

    private JButton getBtnLoadFile() {
        if (btnLoadFile == null) {
            btnLoadFile = new JButton();
            btnLoadFile.setBounds(new Rectangle(0, 0, 191, 31));
            btnLoadFile.setText("Load Image");
            btnLoadFile.setMnemonic('l');
            btnLoadFile.addActionListener(e -> {
                fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    mainWindow.loadImage(fileChooser.getSelectedFile()
                            .getPath());
                }
            });
        }
        return btnLoadFile;
    }

    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setBounds(new Rectangle(0, 280, 191, 31));
            btnExit.setText("Exit");
            btnExit.setMnemonic('e');
            btnExit.addActionListener(e -> System.exit(0));
        }
        return btnExit;
    }

    private JComboBox<BufferedImageOp> getCmbMorph() {
        if (cmbMorph == null) {
            cmbMorph = new JComboBox<>(ImageProcessing.getMorphs());
            cmbMorph.setBounds(new Rectangle(0, 80, 191, 21));
        }
        return cmbMorph;
    }

    private JButton getBtnMorph() {
        if (btnMorph == null) {
            btnMorph = new JButton();
            btnMorph.setBounds(new Rectangle(0, 100, 191, 21));
            btnMorph.setText("Apply Morph");
            btnMorph.setMnemonic('m');
            btnMorph.addActionListener(e -> mainWindow.doOperation(cmbMorph.getItemAt(cmbMorph.getSelectedIndex())));
        }
        return btnMorph;
    }

    private JComboBox<BufferedImageOp> getCmbTransform() {
        if (cmbTransform == null) {
            cmbTransform = new JComboBox<>(ImageProcessing.getTransformations());
            cmbTransform.setBounds(new Rectangle(0, 130, 191, 21));
        }
        return cmbTransform;
    }

    private JButton getBtnTransform() {
        if (btnTransform == null) {
            btnTransform = new JButton();
            btnTransform.setBounds(new Rectangle(0, 150, 191, 21));
            btnTransform.setText("Apply Transformation");
            btnTransform.setMnemonic('t');
            btnTransform.addActionListener(e -> mainWindow.doOperation(cmbTransform
                    .getItemAt(cmbTransform.getSelectedIndex())));
        }
        return btnTransform;
    }
}
