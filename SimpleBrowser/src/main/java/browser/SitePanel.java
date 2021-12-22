package browser;

import browser.Database.Site;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class SitePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Main browser;
    private JComboBox<String> cmbCategory;
    private JList<Site> lstSites;
    private DefaultListModel<Site> listModel;

    SitePanel(Main _browser) {
        browser = _browser;
        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.add(createCmbKategori(), BorderLayout.NORTH);
        this.add(createLstSiteler(), BorderLayout.CENTER);
    }

    private JList<Site> createLstSiteler() {
        listModel = new DefaultListModel<>();
        lstSites = new JList<>(listModel);
        lstSites.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lstSites.addListSelectionListener(e -> {
            String url = getSiteURL();
            if (url != null)
                browser.go(url);
        });
        return lstSites;
    }

    void refreshList() {
        this.populateList(getKategori());
    }

    private void populateList(int kategori) {
        try {
            ArrayList<Site> siteler = Database.getInstance().getSiteler(kategori);
            listModel.removeAllElements();
            for (Site site : siteler) {
                listModel.addElement(site);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JComboBox<String> createCmbKategori() {
        cmbCategory = new JComboBox<>();
        try {
            ArrayList<String> kategoriler = Database.getInstance().getKategoriler();
            for (String string : kategoriler) {
                cmbCategory.addItem(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cmbCategory.addActionListener(e -> populateList(getKategori()));

        return cmbCategory;
    }

    int getKategori() {
        return cmbCategory.getSelectedIndex() + 1;
    }

    String getSiteURL() {
        return lstSites.getSelectedValue().url;
    }
}
