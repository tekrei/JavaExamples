package currencyreader;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtValue = null;
    private JComboBox<Currency> cmbFrom = null;
    private JComboBox<Currency> cmbTo = null;
    private JTextField txtConversionResult = null;
    private JButton btnCrossrate = null;

    private Main() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new Main();
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

        JLabel jLabel1 = new JLabel();
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
        JButton btnConvert = new JButton();
        btnConvert.setBounds(new Rectangle(150, 40, 51, 21));
        btnConvert.setText(">");
        btnConvert.setMnemonic('c');
        btnConvert.addActionListener(e -> {
            Currency kod1Currency = (Currency) cmbFrom.getSelectedItem();
            Currency kod2Currency = (Currency) cmbTo.getSelectedItem();
            float ytl = (kod1Currency.getForexBuying() / kod1Currency.getUnit())
                    * Float.parseFloat(txtValue.getText());
            txtConversionResult
                    .setText(Float.toString(ytl / (kod2Currency.getForexSelling() / kod2Currency.getUnit())));
        });
        this.add(btnConvert, null);

        btnCrossrate = new JButton();
        btnCrossrate.setBounds(new Rectangle(10, 70, 331, 21));
        btnCrossrate.setText("Crossrate:");
        btnCrossrate.setMnemonic('r');
        btnCrossrate.addActionListener(e -> btnCrossrate.setText("Crossrate:"
                + crossrate((Currency) cmbFrom.getSelectedItem(), (Currency) cmbTo.getSelectedItem())));
        this.add(btnCrossrate, null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    private float crossrate(Currency kod1Currency, Currency kod2Currency) {
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

}
