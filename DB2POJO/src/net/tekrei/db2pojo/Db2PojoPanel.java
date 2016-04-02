package net.tekrei.db2pojo;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import net.tekrei.db2pojo.database.DBManager;
import net.tekrei.db2pojo.generator.PojoGenerator;

public class Db2PojoPanel extends JPanel implements ClipboardOwner {

	public String[] defaultDriver = { "com.mysql.jdbc.Driver", "oracle.jdbc.driver.OracleDriver",
			"sun.jdbc.odbc.JdbcOdbcDriver" };
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtURL = null;
	private JLabel lblURL = null;
	private JLabel lblUser = null;
	private JTextField txtUser = null;
	private JLabel lblPassword = null;
	private JPasswordField txtPassword = null;
	private JButton btnConnect = null;
	private JList<String> lstTables = null;
	private JTextArea txtPojo = null;

	private DefaultListModel<String> model;

	private Clipboard clipboard = getToolkit().getSystemClipboard();
	private JTextField txtDriver;
	private JLabel lblTables;
	private JLabel lblGeneratedPojo;

	public Db2PojoPanel() {
		super();
		initialize();
	}

	private void initialize() {
		lblPassword = new JLabel();
		lblPassword.setBounds(new Rectangle(522, 14, 82, 21));
		lblPassword.setText("Password:");
		lblUser = new JLabel();
		lblUser.setBounds(new Rectangle(359, 14, 41, 20));
		lblUser.setText("User:");
		lblURL = new JLabel();
		lblURL.setBounds(new Rectangle(178, 14, 41, 20));
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
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clipboard.setContents(new StringSelection(txtPojo.getText()), null);

			}
		});
		btnCopy.setBounds(new Rectangle(180, 50, 101, 21));
		btnCopy.setBounds(359, 484, 101, 21);
		add(btnCopy);

		JButton btnCut = new JButton();
		btnCut.setText("Cut");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clipboard.setContents(new StringSelection(txtPojo.getText()), null);
				txtPojo.setText("");
			}
		});
		btnCut.setMnemonic(KeyEvent.VK_U);
		btnCut.setBounds(new Rectangle(290, 50, 91, 21));
		btnCut.setBounds(469, 484, 91, 21);
		add(btnCut);

		JButton btnSave = new JButton();
		btnSave.setText("Save File");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// generate file name
				String fileName = PojoGenerator.getInstance().getClassName((String) lstTables.getSelectedValue())
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
			}
		});
		btnSave.setBounds(new Rectangle(390, 50, 101, 21));
		btnSave.setBounds(569, 484, 101, 21);
		add(btnSave);

		JLabel lblDriver = new JLabel("Driver:");
		lblDriver.setBounds(10, 14, 70, 20);
		add(lblDriver);

		txtDriver = new JTextField();
		txtDriver.setText("org.sqlite.JDBC");
		txtDriver.setBounds(60, 14, 114, 20);
		add(txtDriver);
		txtDriver.setColumns(10);
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
			btnConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					connect();
				}
			});
		}
		return btnConnect;
	}

	protected void connect() {
		try {
			DBManager.initManager(txtDriver.getText(), txtURL.getText(), txtUser.getText(),
					new String(txtPassword.getPassword()));
			// Get Table Names
			String[] tables = DBManager.getInstance().getTableNames();
			// Clear list
			model.removeAllElements();
			// Populate table names into list
			for (int i = 0; i < tables.length; i++) {
				model.addElement(tables[i]);
			}
		} catch (Exception e) {
			txtPojo.setText(e.toString());
		}

	}

	private JList<String> getLstTables() {
		if (lstTables == null) {
			model = new DefaultListModel<String>();
			lstTables = new JList<String>(model);
			lstTables.setBounds(new Rectangle(10, 64, 211, 418));
			lstTables.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			lstTables.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					txtPojo.setText(PojoGenerator.getInstance().getPojo((String) lstTables.getSelectedValue()));
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
