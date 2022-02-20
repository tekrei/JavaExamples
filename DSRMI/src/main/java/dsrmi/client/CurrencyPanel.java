package dsrmi.client;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class CurrencyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtValue = null;

    private JComboBox<String> cmbFrom = null;

    private JComboBox<String> cmbTo = null;

    private JTextField txtConversionResult = null;

    private JButton btnConvert = null;

    public CurrencyPanel() {
        super();
        initialize();
    }

    private void initialize() {
        JLabel jLabel1 = new JLabel();
        jLabel1.setBounds(new Rectangle(150, 10, 51, 21));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setText(">>>>");
        this.setSize(350, 100);
        this.setLayout(null);
        this.add(getTxtValue(), null);
        this.add(getCmbFrom(), null);
        this.add(jLabel1, null);
        this.add(getCmbTo(), null);
        this.add(getTxtConversionResult(), null);
        this.add(getBtnConvert(), null);
    }

    private JTextField getTxtValue() {
        if (txtValue == null) {
            txtValue = new JTextField();
            txtValue.setBounds(new Rectangle(10, 40, 131, 21));
        }
        return txtValue;
    }

    private JComboBox<String> getCmbFrom() {
        if (cmbFrom == null) {
            try {
                cmbFrom = new JComboBox<>(RMIManager.getInstance().getCurrencies());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            cmbFrom.setBounds(new Rectangle(10, 10, 131, 21));
        }
        return cmbFrom;
    }

    private JComboBox<String> getCmbTo() {
        if (cmbTo == null) {
            try {
                cmbTo = new JComboBox<>(RMIManager.getInstance().getCurrencies());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            cmbTo.setBounds(new Rectangle(210, 10, 131, 21));
        }
        return cmbTo;
    }

    private JTextField getTxtConversionResult() {
        if (txtConversionResult == null) {
            txtConversionResult = new JTextField();
            txtConversionResult.setBounds(new Rectangle(210, 40, 131, 21));
            txtConversionResult.setEditable(false);
        }
        return txtConversionResult;
    }

    private JButton getBtnConvert() {
        if (btnConvert == null) {
            btnConvert = new JButton();
            btnConvert.setBounds(new Rectangle(150, 40, 51, 21));
            btnConvert.setText(">");
            btnConvert.setMnemonic('c');
            btnConvert.addActionListener(e -> {
                try {
                    txtConversionResult.setText(Float.toString(RMIManager
                            .getInstance().convert(
                                    (String) cmbFrom.getSelectedItem(),
                                    (String) cmbTo.getSelectedItem(),
                                    Float.parseFloat(txtValue.getText()))));
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return btnConvert;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
