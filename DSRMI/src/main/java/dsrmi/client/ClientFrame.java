package dsrmi.client;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public ClientFrame() {
        initialize();
    }

    public static void main(String[] args) {
        new ClientFrame();
    }

    private void initialize() {
        String host = (String) JOptionPane.showInputDialog(this,
                "Please enter RMI registry server host?", "Registry Server Host", JOptionPane.INFORMATION_MESSAGE, null,
                null, "localhost");
        RMIManager.getInstance().init(host, 1099);
        this.setSize(new Dimension(360, 100));
        this.setContentPane(new CurrencyPanel());
        this.setTitle("Information Retrieval Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

} // @jve:decl-index=0:visual-constraint="10,10"
