package translator;

import translator.data.Language;
import translator.data.Translator;
import translator.data.TranslatorFactory;

import javax.swing.*;
import java.awt.*;

/**
 * A simple HTTP page request and parsing example
 * <p>
 * - Apache HttpComponents to request and download page (https://hc.apache.org/)
 * - jsoup to parse HTML file (https://jsoup.org/)
 */
public class Main extends JFrame {
    private JComboBox<Language> cmbFrom = null;
    private JComboBox<Language> cmbTo = null;
    private JComboBox<Translator> cmbService = null;

    private Main() {
        super();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setContentPane(getJContentPane());
        this.setTitle("Online Translator");
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    private JPanel getJContentPane() {
        JPanel jContentPane = new JPanel(null);
        cmbFrom = new JComboBox<>();
        cmbFrom.setBounds(5, 5, 200, 20);
        jContentPane.add(cmbFrom);

        cmbTo = new JComboBox<>();
        cmbTo.setBounds(210, 5, 200, 20);
        jContentPane.add(cmbTo);

        cmbService = new JComboBox<>(TranslatorFactory.getTranslators());
        cmbService.addItemListener(e -> updateLanguages());
        cmbService.setBounds(415, 5, 200, 20);
        jContentPane.add(cmbService);

        JTextArea txtText = new JTextArea();
        JScrollPane pane = new JScrollPane(txtText);
        pane.setBounds(5, 30, 780, 75);
        jContentPane.add(pane);

        JLabel lblTranslatedText = new JLabel();
        JScrollPane pane1 = new JScrollPane(lblTranslatedText);
        pane1.setBounds(new Rectangle(5, 110, 780, 450));
        jContentPane.add(pane1);

        JButton btnTranslate = new JButton();
        btnTranslate.setBounds(620, 5, 165, 20);
        btnTranslate.setText("Translate");
        btnTranslate.setMnemonic('T');
        btnTranslate.addActionListener(e -> {
            String from = cmbFrom.getItemAt(cmbFrom.getSelectedIndex()).getLanguage();
            String to = cmbTo.getItemAt(cmbTo.getSelectedIndex()).getLanguage();
            String result = cmbService.getItemAt(cmbService.getSelectedIndex()).translate(txtText.getText(), from, to);
            lblTranslatedText.setText("<html>" + result + "</html>");
        });
        jContentPane.add(btnTranslate);
        updateLanguages();
        return jContentPane;
    }

    private void updateLanguages() {
        cmbFrom.removeAllItems();
        cmbTo.removeAllItems();
        for (Language language : cmbService.getItemAt(cmbService.getSelectedIndex()).getLanguages()) {
            cmbFrom.addItem(language);
            cmbTo.addItem(language);
        }
        cmbFrom.setSelectedIndex(0);
        cmbTo.setSelectedIndex(1);
    }

}
