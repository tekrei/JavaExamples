package browser;

import browser.Database.Site;

import javax.swing.*;

class SiteButtonPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Main tarayici;

    SiteButtonPanel(Main _t) {
        tarayici = _t;
        initialize();
    }

    private void initialize() {
        JButton btn = new JButton("Add");
        btn.addActionListener(e -> {
            String ad = (String) JOptionPane.showInputDialog(tarayici, "Site adı:", "Site adı",
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            String adres = (String) JOptionPane.showInputDialog(tarayici, "Site adresi:", "Site adresi",
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            // If a string was returned, say so.
            if ((ad != null) && (ad.length() > 0) && ((adres != null) && (adres.length() > 0))) {
                tarayici.addSite(new Site(ad, adres));
            }
        });
        this.add(btn);
        btn = new JButton("Remove");
        btn.addActionListener(arg0 -> tarayici.removeSite());
        this.add(btn);
    }
}
