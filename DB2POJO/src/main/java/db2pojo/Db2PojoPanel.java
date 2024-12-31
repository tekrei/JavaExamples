package db2pojo;

import db2pojo.database.DBManager;
import db2pojo.generator.PojoGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;

public class Db2PojoPanel extends JPanel implements ClipboardOwner {

    private static final long serialVersionUID = 1L;
    private final String[] defaultDriver = {"org.sqlite.JDBC", "org.mariadb.jdbc.Driver",
            "oracle.jdbc.driver.OracleDriver", "sun.jdbc.odbc.JdbcOdbcDriver"};
    private final Clipboard clipboard = getToolkit().getSystemClipboard();
    private JTextField txtURL = null;
    private JTextField txtUser = null;
    private JPasswordField txtPassword = null;
    private JButton btnConnect = null;
    private JList<String> lstTables = null;
    private JTextArea txtPojo = null;
    private DefaultListModel<String> model;
    private JComboBox<String> cmbDriver;
    private JLabel lblTables;
    private JLabel lblGeneratedPojo;

    Db2PojoPanel() {
        super();
        initialize();
    }

    private void initialize() {
        cmbDriver = new JComboBox<>(defaultDriver);
        cmbDriver.setBounds(5, 10, 170, 20);
        add(cmbDriver);
        JLabel lblPassword = new JLabel();
        lblPassword.setBounds(new Rectangle(522, 10, 82, 21));
        lblPassword.setText("Password:");
        JLabel lblUser = new JLabel();
        lblUser.setBounds(new Rectangle(359, 10, 41, 20));
        lblUser.setText("User:");
        JLabel lblURL = new JLabel();
        lblURL.setBounds(new Rectangle(178, 10, 41, 20));
        lblURL.setText("URL:");
        this.setLayout(null);
        this.setSize(new Dimension(800, 505));
        this.add(getTxtURL(), null);
        this.add(lblURL, null);
        this.add(lblUser, null);
        this.add(getTxtUser(), null);
        this.add(lblPassword, null);
        this.add(getTxtPassword(), null);
        this.add(getBtnConnect(), null);
        this.add(getLstTables(), null);
        this.add(getTxtPojo(), null);

        JButton btnCopy = new JButton();
        btnCopy.setText("Copy");
        btnCopy.setMnemonic(KeyEvent.VK_O);
        btnCopy.addActionListener(e -> clipboard.setContents(new StringSelection(txtPojo.getText()), null));
        btnCopy.setBounds(new Rectangle(180, 50, 101, 21));
        btnCopy.setBounds(359, 484, 101, 21);
        add(btnCopy);

        JButton btnCut = new JButton();
        btnCut.setText("Cut");
        btnCopy.addActionListener(e -> {
            clipboard.setContents(new StringSelection(txtPojo.getText()), null);
            txtPojo.setText("");
        });
        btnCut.setMnemonic(KeyEvent.VK_U);
        btnCut.setBounds(new Rectangle(290, 50, 91, 21));
        btnCut.setBounds(469, 484, 91, 21);
        add(btnCut);

        JButton btnSave = new JButton();
        btnSave.setText("Save File");
        btnSave.setMnemonic(KeyEvent.VK_S);
        btnSave.addActionListener(e -> {
            // generate file name
            String fileName = PojoGenerator.getInstance().getClassName(lstTables.getSelectedValue())
                    + ".java";
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(fileName));
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().equalsIgnoreCase(fileName)) {
                    JOptionPane.showMessageDialog(null,
                            "You've changed file name. File name must be same with class name!");
                }
                try {
                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    writer.write(txtPojo.getText());
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnSave.setBounds(new Rectangle(390, 50, 101, 21));
        btnSave.setBounds(569, 484, 101, 21);
        add(btnSave);

        add(getLblTables());
        add(getLblGeneratedPojo());

    }

    private JTextField getTxtURL() {
        if (txtURL == null) {
            txtURL = new JTextField();
            txtURL.setText("jdbc:sqlite:sites");
            txtURL.setBounds(new Rectangle(213, 14, 141, 20));
        }
        return txtURL;
    }

    private JTextField getTxtUser() {
        if (txtUser == null) {
            txtUser = new JTextField();
            txtUser.setBounds(new Rectangle(398, 14, 121, 20));
        }
        return txtUser;
    }

    private JTextField getTxtPassword() {
        if (txtPassword == null) {
            txtPassword = new JPasswordField();
            txtPassword.setBounds(new Rectangle(603, 14, 82, 21));
        }
        return txtPassword;
    }

    private JButton getBtnConnect() {
        if (btnConnect == null) {
            btnConnect = new JButton();
            btnConnect.setBounds(new Rectangle(687, 13, 101, 21));
            btnConnect.setMnemonic(KeyEvent.VK_C);
            btnConnect.setText("Connect");
            btnConnect.addActionListener(e -> connect());
        }
        return btnConnect;
    }

    private void connect() {
        try {
            DBManager.initManager(cmbDriver.getSelectedItem().toString(), txtURL.getText(), txtUser.getText(),
                    new String(txtPassword.getPassword()));
            // Get Table Names
            String[] tables = DBManager.getInstance().getTableNames();
            // Clear list
            model.removeAllElements();
            // Populate table names into list
            for (String table : tables) {
                model.addElement(table);
            }
        } catch (Exception e) {
            txtPojo.setText(e.toString());
        }

    }

    private JList<String> getLstTables() {
        if (lstTables == null) {
            model = new DefaultListModel<>();
            lstTables = new JList<>(model);
            lstTables.setBounds(new Rectangle(10, 64, 211, 418));
            lstTables.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            lstTables.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtPojo.setText(PojoGenerator.getInstance().getPojo(lstTables.getSelectedValue()));
                }
            });
        }
        return lstTables;
    }

    private JScrollPane getTxtPojo() {
        if (txtPojo == null) {
            txtPojo = new JTextArea();
        }
        JScrollPane pane = new JScrollPane(txtPojo);
        pane.setBounds(new Rectangle(230, 64, 551, 418));
        return pane;
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Clipboard contents replaced!");
    }

    private JLabel getLblTables() {
        if (lblTables == null) {
            lblTables = new JLabel("Tables");
            lblTables.setHorizontalAlignment(SwingConstants.CENTER);
            lblTables.setBounds(70, 46, 70, 15);
        }
        return lblTables;
    }

    private JLabel getLblGeneratedPojo() {
        if (lblGeneratedPojo == null) {
            lblGeneratedPojo = new JLabel("Generated POJO");
            lblGeneratedPojo.setHorizontalAlignment(SwingConstants.CENTER);
            lblGeneratedPojo.setBounds(460, 46, 196, 15);
        }
        return lblGeneratedPojo;
    }
}
