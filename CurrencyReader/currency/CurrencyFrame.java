package currency;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CurrencyFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtValue = null;
	private JComboBox<Currency> cmbFrom = null;
	private JLabel jLabel1 = null;
	private JComboBox<Currency> cmbTo = null;
	private JTextField txtConversionResult = null;
	private JButton btnConvert = null;
	private JButton btnCrossrate = null;

	public CurrencyFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(375, 150);
		this.setLayout(null);

		txtValue = new JTextField();
		txtValue.setBounds(new Rectangle(10, 40, 131, 21));
		this.add(txtValue, null);

		cmbFrom = new JComboBox<>(CurrencyReader.getInstance().getCurrencies());
		cmbFrom.setBounds(new Rectangle(10, 10, 131, 21));
		this.add(cmbFrom, null);

		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(150, 10, 51, 21));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel1.setText(">>>>");
		this.add(jLabel1, null);

		cmbTo = new JComboBox<>(CurrencyReader.getInstance().getCurrencies());
		cmbTo.setBounds(new Rectangle(210, 10, 131, 21));
		this.add(cmbTo, null);

		txtConversionResult = new JTextField();
		txtConversionResult.setBounds(new Rectangle(210, 40, 131, 21));
		txtConversionResult.setEditable(false);
		this.add(txtConversionResult, null);
		btnConvert = new JButton();
		btnConvert.setBounds(new Rectangle(150, 40, 51, 21));
		btnConvert.setText(">");
		btnConvert.setMnemonic('c');
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Currency kod1Currency = (Currency) cmbFrom.getSelectedItem();
				Currency kod2Currency = (Currency) cmbTo.getSelectedItem();
				float ytl = (kod1Currency.getForexBuying() / kod1Currency.getUnit())
						* Float.parseFloat(txtValue.getText());
				txtConversionResult
						.setText(Float.toString(ytl / (kod2Currency.getForexSelling() / kod2Currency.getUnit())));
			}
		});
		this.add(btnConvert, null);

		btnCrossrate = new JButton();
		btnCrossrate.setBounds(new Rectangle(10, 70, 331, 21));
		btnCrossrate.setText("Crossrate:");
		btnCrossrate.setMnemonic('r');
		btnCrossrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCrossrate.setText("Crossrate:"
						+ crossrate((Currency) cmbFrom.getSelectedItem(), (Currency) cmbTo.getSelectedItem()));
			}
		});
		this.add(btnCrossrate, null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void refreshCurrencies() {
		Vector<Currency> codes = CurrencyReader.getInstance().getCurrencies();
		cmbFrom.removeAllItems();
		cmbTo.removeAllItems();
		for (Currency currency : codes) {
			cmbFrom.addItem(currency);
			cmbTo.addItem(currency);
		}
	}

	public float crossrate(Currency kod1Currency, Currency kod2Currency) {
		if (kod1Currency.getCurrencyCode().equals("USD")) {
			return kod2Currency.getCrossrateUSD();
		}
		if (kod2Currency.getCurrencyCode().equals("USD")) {
			return kod1Currency.getCrossrateUSD();
		}
		if (kod1Currency.getCrossrateUSD() > 0 && kod2Currency.getCrossrateUSD() > 0) {
			return kod2Currency.getCrossrateUSD() / kod1Currency.getCrossrateUSD();
		}
		if (kod1Currency.getCrossrateUSD() == 0 && kod1Currency.getCrossrateUSD() == 0) {
			return kod2Currency.getCrossrateOther() / kod1Currency.getCrossrateOther();
		}
		if (kod1Currency.getCrossrateUSD() == 0) {
			return kod2Currency.getCrossrateUSD() * kod1Currency.getCrossrateOther();
		}
		if (kod2Currency.getCrossrateUSD() == 0) {
			return kod1Currency.getCrossrateUSD() * kod2Currency.getCrossrateOther();
		}
		return 0;
	}

	public static void main(String[] args) {
		new CurrencyFrame();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
